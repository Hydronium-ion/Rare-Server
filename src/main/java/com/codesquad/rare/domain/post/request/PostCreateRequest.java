package com.codesquad.rare.domain.post.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class PostCreateRequest {

  private String title;

  private String content;

  private String thumbnail;

  private Long authorId;

  private String tags;
}
