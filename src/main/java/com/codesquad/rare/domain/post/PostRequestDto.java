package com.codesquad.rare.domain.post;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostRequestDto {

  private Long id;

  private String title;

  private String content;

  private String thumbnail;

  private String author;

  private Integer views;

  private Integer likes;

  private String tags;

  private LocalDateTime createdTimeAt;
}
