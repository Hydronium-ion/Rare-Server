package com.codesquad.rare.domain.account;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public ApiResult<AccountCreateResponse> create(
      @Valid @RequestBody AccountCreateRequest accountCreateRequest) {
    return OK(accountService.create(accountCreateRequest));
  }
}
