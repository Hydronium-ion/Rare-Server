package com.codesquad.rare.domain.account;

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

  public AccountCreateResponse create(AccountCreateRequest accountCreateRequest) {

    Account savedAccount = accountRepository.save(Account.toEntity(accountCreateRequest));
    AccountCreateResponse accountCreateResponse = new AccountCreateResponse();

    return accountCreateResponse;
  }
}
