package com.codesquad.rare.domain.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String filePath;

  private boolean isPostImage;

  @Builder
  public Image(Long id, String filePath, boolean isPostImage) {
    this.id = id;
    this.filePath = filePath;
    this.isPostImage = isPostImage;
  }
}
