package com.codesquad.rare.domain.account;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountCreateRequest {

  private String name;

  private String email;

  private String avatarUrl;
}
