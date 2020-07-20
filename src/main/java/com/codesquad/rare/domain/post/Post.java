package com.codesquad.rare.domain.post;

import com.codesquad.rare.common.BaseTimeEntity;
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
import org.springframework.data.annotation.CreatedDate;

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

  @CreatedDate
  private LocalDateTime createdTimeAt;
}
