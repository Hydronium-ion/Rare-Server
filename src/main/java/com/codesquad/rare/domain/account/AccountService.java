package com.codesquad.rare.domain.account;

import com.codesquad.rare.config.JwtService;
import com.codesquad.rare.domain.account.oauth.github.GitHubAccessToken;
import com.codesquad.rare.domain.account.oauth.github.GitHubOAuthService;
import com.codesquad.rare.domain.account.request.AccountUpdateRequest;
import com.codesquad.rare.domain.account.response.AccountIdResponse;
import com.codesquad.rare.domain.account.response.AccountResponse;
import com.codesquad.rare.error.exeception.NotFoundException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AccountService {

  private final AccountRepository accountRepository;
  private final GitHubOAuthService gitHubOAuthService;
  private final JwtService jwtService;

  public AccountService(AccountRepository accountRepository, GitHubOAuthService gitHubOAuthService,
      JwtService jwtService) {
    this.accountRepository = accountRepository;
    this.gitHubOAuthService = gitHubOAuthService;
    this.jwtService = jwtService;
  }

  @Transactional
  public String create(String code) {
    HttpHeaders httpHeaders = new HttpHeaders();
    GitHubAccessToken gitHubAccessToken = gitHubOAuthService.getAccessToken(code);
    log.info("##### Access Token {}, {}", gitHubAccessToken.getTokenType(),
        gitHubAccessToken.getAccessToken());
    httpHeaders.set("Authorization", "token " + gitHubAccessToken.getAccessToken());

    ResponseEntity<Map> accountMap = gitHubOAuthService
        .getAccountMap(httpHeaders, gitHubAccessToken.getAccessToken());
    Account account = gitHubOAuthService.verifyAccountData(httpHeaders, accountMap);
    gitHubOAuthService.createAccount(account);
    return jwtService.makeJwt(account);
  }

  public AccountResponse findById(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new NotFoundException(Account.class, accountId));
    return new AccountResponse(account);
  }

  @Transactional
  public AccountIdResponse update(Long accountId, AccountUpdateRequest accountUpdateRequest) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new NotFoundException(Account.class, accountId));
    account.update(accountUpdateRequest);
    Account savedAccount = accountRepository.save(account);
    return new AccountIdResponse(savedAccount.getId());
  }
}