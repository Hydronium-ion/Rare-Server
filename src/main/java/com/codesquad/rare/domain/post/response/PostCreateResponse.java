package com.codesquad.rare.domain.post.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostCreateResponse {

  private Long postId;

  public PostCreateResponse(Long postId) {
    this.postId = postId;
  }
}
