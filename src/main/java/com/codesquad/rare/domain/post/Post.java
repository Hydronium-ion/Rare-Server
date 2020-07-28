package com.codesquad.rare.domain.post;

import com.codesquad.rare.domain.account.Account;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@JsonPropertyOrder({
    "id",
    "title",
    "content",
    "thumbnail",
    "author",
    "views",
    "likes",
    "tags",
    "createdAt"
})
@Entity
public class Post {

  @Id
  @Column(name = "post_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //TODO title not nullable length 설정
  private String title;

  //TODO content nullable, length 설정
  private String content;

  @Lob
  private String thumbnail;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account author;

  private Integer views;

  private Integer likes;

  private String tags;

  private LocalDateTime createdAt;

  public static Post from(PostRequestDto postRequestDto) {
    return Post.builder()
        .title(postRequestDto.getTitle())
        .content(postRequestDto.getContent())
        .thumbnail(postRequestDto.getThumbnail())
        .author(postRequestDto.getAuthor())
        .views(0)
        .likes(0)
        .tags(postRequestDto.getTags())
        .createdAt(LocalDateTime.now())
        .build();
  }
}
