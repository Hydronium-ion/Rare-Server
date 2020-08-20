package com.codesquad.rare.domain.image;

import com.codesquad.rare.common.api.ApiResult;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@AllArgsConstructor
@Slf4j
public class ImageController {

  private S3Service s3Service;
  private ImageService imageService;

  @PostMapping("/images")
  public ApiResult uploadPostImage(HttpServletRequest request, MultipartHttpServletRequest request2,
      ImageDto imageDto, @RequestParam("imageFile") MultipartFile file) throws IOException {

    String imgPath = s3Service.upload(file, imageDto.isPostImage());
    imageDto.setFilePath(imgPath);
    imageService.save(imageDto);

    return ApiResult.OK(true);
  }
}
