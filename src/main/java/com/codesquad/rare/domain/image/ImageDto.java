package com.codesquad.rare.domain.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {

  private String filePath;
  @JsonProperty("isPostImage")
  private boolean isPostImage;

  @Builder
  public ImageDto(String filePath, boolean isPostImage) {
    this.filePath = filePath;
    this.isPostImage = isPostImage;
  }

  public Image toEntity(ImageDto imageDto){
    return Image.builder()
        .filePath(imageDto.getFilePath())
        .isPostImage(imageDto.isPostImage())
        .build();
  }
}
