package com.codesquad.rare.common.api;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiError {

  private final String message;

  private final int status;

  private ApiError(String message, HttpStatus status) {
    this.message = message;
    this.status = status.value();
  }

  public ApiError(Exception exception, HttpStatus status) {
    this(exception.getMessage(), status);
  }
}
