package com.codesquad.rare.domain.post.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostIdResponse {

  private Long postId;

  public PostIdResponse(final Long postId) {
    this.postId = postId;
  }
}
