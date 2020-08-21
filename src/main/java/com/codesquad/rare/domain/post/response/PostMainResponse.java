package com.codesquad.rare.domain.post.response;

import com.codesquad.rare.domain.account.Account;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMainResponse {

  private Long id;

  private String title;

  private String content;

  private String thumbnail;

  private Account author;

  private Integer views;

  private Integer likes;

  private LocalDateTime createdAt;

  @Builder
  private PostMainResponse(
      final Long id, final String title, final String content, final String thumbnail,
      final Account author, final Integer views, final Integer likes, final LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.thumbnail = thumbnail;
    this.author = author;
    this.views = views;
    this.likes = likes;
    this.createdAt = createdAt;
  }
}
