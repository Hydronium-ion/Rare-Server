package com.codesquad.rare.domain.post;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    "createdTimeAt"
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

  private String thumbnail;

  private String author;

  private Integer views;

  private Integer likes;

  private String tags;

//  @CreationTimestamp
  private LocalDateTime createdTimeAt;

  public static Post from(PostRequestDto postRequestDto) {
    return Post.builder()
        .id(postRequestDto.getId())
        .title(postRequestDto.getTitle())
        .content(postRequestDto.getContent())
        .thumbnail(postRequestDto.getThumbnail())
        .author(postRequestDto.getAuthor())
        .views(postRequestDto.getViews())
        .likes(postRequestDto.getLikes())
        .tags(postRequestDto.getTags())
        .createdTimeAt(LocalDateTime.now())
        .build();
  }
}
