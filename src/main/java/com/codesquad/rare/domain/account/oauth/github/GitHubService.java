package com.codesquad.rare.domain.account.oauth.github;

import com.codesquad.rare.config.JwtService;
import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.account.AccountRepository;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class GitHubService {

  private final AccountRepository accountRepository;
  private final JwtService jwtService;

  @Value("${github.client_id}")
  private String clientId;
  @Value("${github.client_secret}")
  private String clientSecret;

  public GitHubService(AccountRepository accountRepository,
      JwtService jwtService) {
    this.accountRepository = accountRepository;
    this.jwtService = jwtService;
  }

  public String create(String code, HttpServletResponse response) throws Exception {

    GitHubAccessToken gitHubAccessToken = getAccessToken(code);
    log.info("##### Access Token {}, {}", gitHubAccessToken.getTokenType(),
        gitHubAccessToken.getAccessToken());

    ResponseEntity<Map> resultMap = this.getResultMap(gitHubAccessToken.getAccessToken());
    Account account = Account.from(resultMap);
    this.createAccount(account);

    return jwtService.makeJwt(account);
  }

  public GitHubAccessToken getAccessToken(String code) {
    log.info("##### local:  {}, {}", clientId, clientSecret);
    String URL = "https://github.com/login/oauth/access_token";

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    Map<String, String> header = new HashMap<>();
    header.put("Accept", "application/json");
    headers.setAll(header);

    MultiValueMap<String, String> bodies = new LinkedMultiValueMap<>();
    Map<String, String> body = new HashMap<>();
    body.put("client_id", clientId);
    body.put("client_secret", clientSecret);
    body.put("code", code);
    bodies.setAll(body);

    HttpEntity<?> request = new HttpEntity<>(bodies, headers);
    ResponseEntity<?> response = new RestTemplate()
        .postForEntity(URL, request, GitHubAccessToken.class);
    return (GitHubAccessToken) response.getBody();
  }

  public ResponseEntity<Map> getResultMap(String accessToken) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders header = new HttpHeaders();
    HttpEntity<?> entity = new HttpEntity<>(header);

    UriComponents sendAccessTokenUrl = UriComponentsBuilder
        .fromHttpUrl("https://api.github.com/user?access_token=" + accessToken).build();

    try {
      return restTemplate
          .exchange(sendAccessTokenUrl.toString(), HttpMethod.GET, entity, Map.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      log.info("##### HttpErrorException: {}", e.getMessage());
    } catch (Exception e) {
      log.info("##### Exception: {}", e.getMessage());
    }
    return null;
  }

  @Transactional
  public void createAccount(Account account) {
    if ((accountRepository.findAccountByEmail(account.getEmail()) == null)) {
      accountRepository.save(account);
    }
  }
}
