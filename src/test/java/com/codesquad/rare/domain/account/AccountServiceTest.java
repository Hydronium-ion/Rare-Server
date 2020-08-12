package com.codesquad.rare.domain.account;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codesquad.rare.domain.account.oauth.github.GitHubService;
import com.codesquad.rare.error.exeception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

  @MockBean
  GitHubService gitHubService;

}
