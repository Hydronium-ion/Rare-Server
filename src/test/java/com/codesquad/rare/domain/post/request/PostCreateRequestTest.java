package com.codesquad.rare.domain.post.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PostCreateRequestTest {

  private final Logger log = LoggerFactory.getLogger(PostCreateRequestTest.class);

  private Validator validator;

  @BeforeEach
  void setValidator() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @DisplayName("isPublic이 Null이면 오류를 반환한다.")
  @Test
  void isPublic_should_NotNull() {
    //given
    PostCreateRequest request = PostCreateRequest.builder()
        .title("타이틀입니다일 (필수)")
        .subTitle("보조 타이틀 입니다(필수)")
        .content("내용")
        .authorId(1L)
        .tags("1번")
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .isPublic(null)
        .build();

    //when
    Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(request);
    log.debug(violations.iterator().next().getMessage());

    //then
    assertThat(violations.size()).isEqualTo(1);
  }

  @DisplayName("title, subTitle 이 없을 경우 오류를 반환한다")
  @Test
  void title_should_NotNull() {
    //given
    PostCreateRequest request = PostCreateRequest.builder()
        .content("내용")
        .authorId(1L)
        .tags("1번")
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .isPublic(true)
        .build();

    //when
    Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(request);

    //then
    assertThat(violations.size()).isEqualTo(2);
  }
}
