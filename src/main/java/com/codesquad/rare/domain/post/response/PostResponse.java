package com.codesquad.rare.domain.post.response;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.post.Post;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

  private Long id;
  private String title;
  private String subTitle;
  private String content;
  private String thumbnail;
  private Account author;
  private Integer views;
  private Integer likes;
  private String tags;
  private LocalDateTime createdAt;

  @Builder
  private PostResponse(
      final Long id, final String title, final String subTitle, final String content,
      final String thumbnail, final Account author, final Integer views, final Integer likes,
      final String tags, final LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.content = content;
    this.thumbnail = thumbnail;
    this.author = author;
    this.views = views;
    this.likes = likes;
    this.tags = tags;
    this.createdAt = createdAt;
  }
}
