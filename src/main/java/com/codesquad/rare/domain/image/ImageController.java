package com.codesquad.rare.domain.image;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.error.exeception.EmptyValueException;
import com.codesquad.rare.error.exeception.NotFoundException;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
public class ImageController {

  private S3Service s3Service;
  private ImageService imageService;

  @PostMapping("/images")
  public ApiResult<String> uploadPostImage(
      ImageDto imageDto, @RequestParam("imageFile") MultipartFile file) {

    if (file.isEmpty()) {
      throw new EmptyValueException("Image file not found. Unable to upload.");
    }

    String imgPath = s3Service.upload(file, imageDto.isPostImage());
    imageDto.setFilePath(imgPath);
    imageService.save(imageDto);

    return ApiResult.OK(imageDto.getFilePath());
  }
}
