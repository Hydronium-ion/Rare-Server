package com.codesquad.rare.domain.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bucket {

  USER(BucketPath.user), POST(BucketPath.post);

  private String value;
}
