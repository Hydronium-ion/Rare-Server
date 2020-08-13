package com.codesquad.rare.domain.post.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Builder
  private PostCreateRequest(final String title, final String subTitle,
      final String content, final String thumbnail,
      final Long authorId, final String tags, final Boolean isPublic) {
    this.title = title;
    this.subTitle = subTitle;
    this.content = content;
    this.thumbnail = thumbnail;
    this.authorId = authorId;
    this.tags = tags;
    this.isPublic = isPublic;
  }
}
