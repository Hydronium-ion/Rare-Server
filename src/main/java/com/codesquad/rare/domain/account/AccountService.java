package com.codesquad.rare.domain.account;

import com.codesquad.rare.error.exeception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountDeleteResponse delete(AccountDeleteRequest request) {
    Account account = accountRepository.findById(request.getAccountId())
        .orElseThrow(() -> new NotFoundException(Account.class, request.getAccountId()));
    accountRepository.deleteById(request.getAccountId());
    AccountDeleteResponse response = new AccountDeleteResponse();
    response.setAccountId(request.getAccountId());
    return response;
  }
}
