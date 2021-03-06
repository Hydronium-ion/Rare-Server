package com.codesquad.rare.error;

import static com.codesquad.rare.common.api.ApiResult.ERROR;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.error.exeception.NotFoundException;
import com.codesquad.rare.error.exeception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<ApiResult> newResponse(Exception exception, HttpStatus status) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    return new ResponseEntity<>(ERROR(exception, status), headers, status);
  }

  // 예상 하지 못했던 오류
  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<?> handleException(Exception e) {
    log.error("Unexpected exception occurred: {}", e.getMessage(), e);
    return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ServiceRuntimeException.class)
  public ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException e) {
    if (e instanceof NotFoundException) {
      log.error("NotFound exception occurred: {}", e.getMessage(), e);
      return newResponse(e, HttpStatus.NOT_FOUND);
    }

    log.error("Unexpected service exception occurred: {}", e.getMessage(), e);
    return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  //@Validation Exception handler
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleConstraintViolationException(MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException occurred: {}", e.getMessage(), e);
    return newResponse(e, HttpStatus.BAD_REQUEST);
  }
}
