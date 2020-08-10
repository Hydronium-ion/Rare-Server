package com.codesquad.rare.domain.post;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
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
    "subTitle",
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

  @Column(length = 100)
  private String title;

  private String subTitle;

  @Lob
  private String content;

  @Lob
  private String thumbnail;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account author;

  private Integer views;

  private Integer likes;

  private String tags;

  @JsonIgnore
  private Boolean isPublic;

  private LocalDateTime createdAt;

  public static Post toEntity(PostCreateRequest postCreateRequest, Account author) {
    return Post.builder()
        .title(postCreateRequest.getTitle())
        .subTitle(postCreateRequest.getSubTitle())
        .content(postCreateRequest.getContent())
        .thumbnail(postCreateRequest.getThumbnail())
        .author(author)
        .views(0)
        .likes(0)
        .tags(postCreateRequest.getTags())
        .createdAt(LocalDateTime.now())
        .isPublic(postCreateRequest.getIsPublic())
        .build();
  }
}
