package com.codesquad.rare.domain.account;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AccountController {

  private final AccountService accountService;

  @DeleteMapping
  public ApiResult<AccountDeleteResponse> delete(@RequestBody AccountDeleteRequest request) {
    return OK(accountService.delete(request));
  }

}
