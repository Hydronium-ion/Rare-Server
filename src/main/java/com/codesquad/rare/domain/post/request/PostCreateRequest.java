package com.codesquad.rare.domain.post.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {

  @NotBlank(message = "제목을 반드시 입력해주세요")
  private String title;

  @NotBlank(message = "부제목을 반드시 입력해주세요")
  private String subTitle;

  private String content;

  private String thumbnail;

  @NotNull(message = "작성자의 ID는 반드시 입력해주세요")
  private Long authorId;

  private String tags;

  @NotNull(message = "공개 여부를 반드시 입력해주세요")
  private Boolean isPublic;
}
