package com.codesquad.rare.domain.post.response;

import com.codesquad.rare.domain.account.Account;
import java.time.LocalDateTime;
import lombok.AccessLevel;
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
}
