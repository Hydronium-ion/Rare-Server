package com.codesquad.rare.domain.account;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codesquad.rare.domain.account.oauth.github.GitHubOAuthService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith({SpringExtension.class})
public class AccountServiceTest {

  final Logger log = LoggerFactory.getLogger(AccountServiceTest.class);

  @Autowired
  AccountRepository accountRepository;

  @MockBean
  AccountService accountService;

  @MockBean
  GitHubOAuthService gitHubOAuthService;

}
