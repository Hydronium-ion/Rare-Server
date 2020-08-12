package com.codesquad.rare.domain.account.oauth.github;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.account.AccountRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.DuplicateMemberException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class GitHubService {

  private final AccountRepository accountRepository;

  @Value("${github.client_id}")
  private String clientId;
  @Value("${github.client_secret}")
  private String clientSecret;
  @Value("${github.redirection_url}")
  private String redirectionUrl;

  public void create(String code, HttpServletResponse response) throws IOException, Exception {

    GitHubAccessToken gitHubAccessToken = getAccessToken(code);
    log.info("##### Access Token {}, {}", gitHubAccessToken.getTokenType(),
        gitHubAccessToken.getAccessToken());

    ResponseEntity<Map> resultMap = this.getResultMap(gitHubAccessToken.getAccessToken());

    this.createAccount(resultMap);
    // redirection 이후 예상 과정
    // 1. 환영합니다 (환영합니다 페이지 만들어야 함) -> 일부로 유저에게 한 번 더 로그인을 하게 만듦
    // 2. 바로 메인 페이지 (로그인이 된 상태로 메인 페이지 이동) -> 회원가입 이후 로그인하는 귀찮은 과정 생략시킴
    // JWT를 헤더에 담아 클라이언트단에 보낸다. -> 클라이언트 단은 JWT를 디코드해서 유저 정보 사용
    response.sendRedirect(redirectionUrl);
  }

  public GitHubAccessToken getAccessToken(String code) {
    log.info("##### local: {}, {}, {}", redirectionUrl, clientId, clientSecret);
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
      return restTemplate.exchange(sendAccessTokenUrl.toString(), HttpMethod.GET, entity, Map.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      log.info("##### HttpErrorException: {}", e.getMessage());
    } catch (Exception e) {
      log.info("##### Exception: {}", e.getMessage());
    }
    return null;
  }

  @Transactional
  public void createAccount(ResponseEntity<Map> resultMap) {

    Account account = Account.from(resultMap);

    if (!(accountRepository.findAccountByEmail(account.getEmail()) == null)) {
      return;
    }

    accountRepository.save(account);
  }
}
