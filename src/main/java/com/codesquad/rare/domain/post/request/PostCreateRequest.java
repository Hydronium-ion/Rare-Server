package com.codesquad.rare.domain.post.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {

  @NotBlank(message = "제목을 반드시 입력해주세요")
  private final String title;

  @NotBlank(message = "부제목을 반드시 입력해주세요")
  private final String subTitle;

  private final String content;

  private final String thumbnail;

  @NotNull(message = "작성자의 ID는 반드시 입력해주세요")
  private final Long authorId;

  private final String tags;

  @NotNull(message = "공개 여부를 반드시 입력해주세요")
  private final Boolean isPublic;

  @Builder
  private PostCreateRequest(String title, String subTitle, String content, String thumbnail,
      Long authorId, String tags, Boolean isPublic) {
    this.title = title;
    this.subTitle = subTitle;
    this.content = content;
    this.thumbnail = thumbnail;
    this.authorId = authorId;
    this.tags = tags;
    this.isPublic = isPublic;
  }
}
