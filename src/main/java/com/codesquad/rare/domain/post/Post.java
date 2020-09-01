package com.codesquad.rare.domain.post;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.request.PostUpdateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  public void update(PostUpdateRequest postUpdateRequest) {
    this.title = postUpdateRequest.getTitle();
    this.subTitle = postUpdateRequest.getSubTitle();
    this.content = postUpdateRequest.getContent();
    this.thumbnail = postUpdateRequest.getThumbnail();
    this.tags = postUpdateRequest.getTags();
    this.isPublic = postUpdateRequest.getIsPublic();
  }

  @Builder
  private Post(
      final Long id, final String title, final String subTitle,
      final String content, final String thumbnail,
      final Account author, final int views, final int likes, final String tags,
      final boolean isPublic, final LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.content = content;
    this.thumbnail = thumbnail;
    this.author = author;
    this.views = views;
    this.likes = likes;
    this.tags = tags;
    this.isPublic = isPublic;
    this.createdAt = createdAt;
  }

  public static Post of(PostCreateRequest postCreateRequest, Account author) {
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

  public void addLikes() {
    this.likes++;
  }

  public void removeLikes() {
    this.likes--;
  }
}
