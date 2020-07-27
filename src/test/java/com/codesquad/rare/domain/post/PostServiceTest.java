package com.codesquad.rare.domain.post;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class PostServiceTest {

  private final Logger log = LoggerFactory.getLogger(PostServiceTest.class);

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @DisplayName("포스트 생성 테스트")
  @Test
  public void create() throws Exception {
    //given
    PostRequestDto post = PostRequestDto.builder()
        .id(11L)
        .title("블로그 포스팅 테스트1")
        .content("블로그에 글을 적는 건 즐거워")
        .thumbnail("thumbnail 이미지")
        .author("최한울")
        .views(85)
        .likes(3)
        .tags("hamill")
        .createdTimeAt(LocalDateTime.now())
        .build();

    //when
    Post result = postService.save(post);

    //then
    assertThat(result.getId()).isEqualTo(11L);
  }

  @DisplayName("같은 포스트 반복 생성 테스트")
  @RepeatedTest(value = 10, name = "{currentRepetition}/{totalRepetitions} 번째 테스트")
  void create_tenTimes_AllPass(RepetitionInfo repetitionInfo) throws Exception {
    //given
    Long createdDefaultPost = 10L; // 처음 생성 되는 글 10개

    PostRequestDto post = PostRequestDto.builder()
        .id(repetitionInfo.getCurrentRepetition() + createdDefaultPost)
        .title("블로그 포스팅 테스트1")
        .content("블로그에 글을 적는 건 즐거워")
        .thumbnail("thumbnail 이미지")
        .author("최한울")
        .views(85)
        .likes(3)
        .tags("hamill")
        .createdTimeAt(LocalDateTime.now())
        .build();

    //when
    Post result = postService.save(post);

    //then
    assertThat(result.getId())
        .isEqualTo(repetitionInfo.getCurrentRepetition() + createdDefaultPost);
  }

  @DisplayName("포스트 삭제 테스트")
  @Test
  void delete() throws Exception {
    //given
    Long postId = 1L;

    //when
    Post result = postService.delete(postId);

    //then
    assertThat(result.getId()).isEqualTo(postId);
  }

  @DisplayName("포스트 반복 삭제 테스트")
  @RepeatedTest(value = 3,name = "{currentRepetition}/{totalRepetitions} 번째 테스트")
  void delete_third_OnePassTheOthersFailure() {
    System.out.println("히히");
    //given
    Long postId = 1L;

    //when
    Post result = postService.delete(postId);

    //then
    assertThat(result.getId()).isEqualTo(postId);
  }
}