package com.codesquad.rare.domain.post.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateResponse {

  private Long postId;

  public PostCreateResponse(final Long postId) {
    this.postId = postId;
  }
}
