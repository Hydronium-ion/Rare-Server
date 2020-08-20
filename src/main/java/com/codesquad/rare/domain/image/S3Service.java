package com.codesquad.rare.domain.image;

import static com.codesquad.rare.domain.image.Bucket.POST;
import static com.codesquad.rare.domain.image.Bucket.USER;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.codesquad.rare.error.exeception.FilenameExtensionException;
import com.codesquad.rare.error.exeception.S3FileUploadException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@NoArgsConstructor
@Slf4j
public class S3Service {

  private AmazonS3 s3Client;

  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;

  @Value("${cloud.aws.region.static}")
  private String region;

  @PostConstruct
  public void setS3Client() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

    s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(this.region)
        .build();
  }

  public String upload(final MultipartFile file, final boolean isPostImage) {
    try {
      String bucket = getBucketPath(isPostImage);
      String fileName = file.getOriginalFilename();
      log.debug("##### fileName:{}", fileName);
      verifyFilenameExtension(file);

      byte[] bytes = IOUtils.toByteArray(file.getInputStream());
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(bytes.length);
      objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

      s3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayInputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
      return s3Client.getUrl(bucket, fileName).toString();
    } catch (IOException e) {
      throw new S3FileUploadException("Error: File upload failed");
    }
  }

  private String getBucketPath(boolean isPostImage) {
    if (isPostImage) {
      return POST.getValue();
    }
    return USER.getValue();
  }

  private void verifyFilenameExtension(MultipartFile file) {
    List<String> imageExtensions =
        Arrays.asList("bmp", "rle", "dib", "jpg", "jpeg", "png", "gif", "tif", "tiff", "raw");
    String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
    if (imageExtensions.contains(fileExtension)) {
      return;
    }
    throw new FilenameExtensionException();
  }
}
