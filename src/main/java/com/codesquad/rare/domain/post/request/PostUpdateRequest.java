package com.codesquad.rare.domain.post.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {

  @NotBlank(message = "제목을 반드시 입력해주세요")
  private String title;

  @NotBlank(message = "부제목을 반드시 입력해주세요")
  private String subTitle;

  private String content;

  private String thumbnail;

  private String tags;

  @NotNull(message = "공개 여부를 반드시 입력해주세요")
  private Boolean isPublic;
}
