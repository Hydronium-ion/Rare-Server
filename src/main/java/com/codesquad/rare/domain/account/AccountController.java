package com.codesquad.rare.domain.account;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.domain.account.request.AccountUpdateRequest;
import com.codesquad.rare.domain.account.response.AccountResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("{id}")
  public ApiResult<AccountResponse> findById(@PathVariable(value = "id") Long accountId) {
    return OK(accountService.findById(accountId));
  }

  @PutMapping("{id}")
  public ApiResult update(@PathVariable(value = "id") Long accountId,
      @Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
    return OK(accountService.update(accountId, accountUpdateRequest));
  }
}