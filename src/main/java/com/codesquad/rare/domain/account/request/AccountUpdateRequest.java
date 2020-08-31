package com.codesquad.rare.domain.account.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateRequest {

  @NotBlank(message = "이름을 반드시 입력해주세요")
  private String name;

  @NotBlank(message = "이메일은 반드시 입력해주세요")
  private String email;

  private String avatarUrl;
}
