package com.codesquad.rare.domain.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BucketPath {

  public static String user;
  public static String post;

  @Value("${cloud.aws.s3.bucket-user}")
  public void setUser(String user) {
    this.user = user;
  }
  @Value("${cloud.aws.s3.bucket-post}")
  public void setPost(String post) {
    this.post = post;
  }
}
