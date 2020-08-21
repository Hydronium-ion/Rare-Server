package com.codesquad.rare.domain.image;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {

  private ImageRepository imageRepository;

  public void save(ImageDto imageDto) {
    imageRepository.save(imageDto.toEntity(imageDto));
  }
}
