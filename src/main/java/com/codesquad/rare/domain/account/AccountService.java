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

  @Transactional
  public AccountDeleteResponse delete(Long accountId) {
    Account account = accountRepository.findById(accountId).orElseThrow(
        () -> new NotFoundException(Account.class, accountId));
    accountRepository.delete(account);
    return new AccountDeleteResponse(accountId);
  }
}
