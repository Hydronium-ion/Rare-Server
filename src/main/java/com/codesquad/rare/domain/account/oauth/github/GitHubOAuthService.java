package com.codesquad.rare.domain.account.oauth.github;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.account.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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
@Transactional(readOnly = true)
@Slf4j
public class GitHubOAuthService {

  private final AccountRepository accountRepository;
  private final String GITHUB_AUTHORIZATION_URL = "https://github.com/login/oauth/authorize";
  private final String GITHUB_SCOPE = "user:email";
  private final String GITHUB_GET_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
  private final String GITHUB_GET_USER_DATA_URL = "https://api.github.com/user";

  @Value("${github.client_id}")
  private String clientId;
  @Value("${github.client_secret}")
  private String clientSecret;

  private RestTemplate restTemplate = new RestTemplate();
  private ObjectMapper objectMapper = new ObjectMapper();

  public GitHubOAuthService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void sendRedirect(HttpServletResponse response) throws IOException {
    response.sendRedirect(GITHUB_AUTHORIZATION_URL
        + "?client_id=" + clientId + "&scope=" + GITHUB_SCOPE);
  }

  public GitHubAccessToken getAccessToken(String code) {
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

    HttpEntity<?> entity = new HttpEntity<>(bodies, headers);
    ResponseEntity<?> response = new RestTemplate()
        .postForEntity(GITHUB_GET_ACCESS_TOKEN_URL, entity, GitHubAccessToken.class);
    return (GitHubAccessToken) response.getBody();
  }

  public ResponseEntity<Map> getAccountMap(HttpHeaders httpHeaders, String accessToken) {
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    UriComponents sendAccessTokenUrl = UriComponentsBuilder
        .fromHttpUrl(GITHUB_GET_USER_DATA_URL + "?access_token=" + accessToken).build();

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

  private String getEmailFromGitHub(HttpHeaders httpHeaders) {
    String email = restTemplate
        .exchange(GITHUB_GET_USER_DATA_URL + "/emails", HttpMethod.GET,
            new HttpEntity<>(httpHeaders), String.class).getBody();

    try {
      JsonNode emailNode = objectMapper.readTree(email);
      for (JsonNode jsonNode : emailNode) {
        if (jsonNode.get("primary").asBoolean()) {
          return jsonNode.get("email").textValue();
        }
      }
    } catch (JsonProcessingException e) {
      log.error("##### Wrong Json format.", e);
    }
    return null;
  }

  public Account verifyAccountData(HttpHeaders httpHeaders, ResponseEntity<Map> accountMap) {
    if (accountMap.getBody().get("email") == null) {
      String email = getEmailFromGitHub(httpHeaders);
      return Account.of(accountMap, email);
    } else {
      return Account.from(accountMap);
    }
  }
}
