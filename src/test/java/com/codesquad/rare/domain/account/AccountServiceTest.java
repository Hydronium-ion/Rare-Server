package com.codesquad.rare.domain.account;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codesquad.rare.error.exeception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ExtendWith({SpringExtension.class})
public class AccountServiceTest {

  final Logger log = LoggerFactory.getLogger(AccountServiceTest.class);

  @Autowired
  AccountRepository accountRepository;

  @MockBean
  AccountService accountService;

  @DisplayName("유저 삭제 테스트")
  @Transactional
  @Sql("/user-sample-data.sql")
  @Test
  public void delete_account() throws Exception {
    //given
    Account account = accountRepository.findById(1L)
        .orElseThrow(() -> new NotFoundException(Account.class, 1L));
    //when
    accountRepository.delete(account);

    assertThrows(NotFoundException.class, () -> accountRepository.findById(1L)
        .orElseThrow(() -> new NotFoundException(Account.class, 1L)));
  }
}
