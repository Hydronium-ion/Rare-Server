package com.codesquad.rare.domain.account.response;

import com.codesquad.rare.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponse {

  private Long id;

  private String name;

  private String email;

  private String avatarUrl;

  @Builder
  public AccountResponse(final Account account) {
    this.id = account.getId();
    this.name = account.getName();
    this.email = account.getEmail();
    this.avatarUrl = account.getAvatarUrl();
  }
}
