package com.codesquad.rare.domain.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.codesquad.rare.domain.account.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class PostServiceTest {

  final Logger log = LoggerFactory.getLogger(PostServiceTest.class);

  @MockBean
  PostRepository postRepository;

  /**
   * 같은 포스트라도 반복적으로 ID 값만 다르다면 모두 생성할 수 있어야 합니다. 같은 포스트를 10번 반복 생성해보는 테스트입니다.
   */
  @DisplayName("같은 포스트 반복 생성 테스트")
  @RepeatedTest(value = 10, name = "{currentRepetition}/{totalRepetitions} 번째 테스트")
  void create_tenTimes_AllPass(RepetitionInfo repetitionInfo) {
    //given
    Long postId = (long) repetitionInfo.getCurrentRepetition();

    Account won = Account.builder()
        .id(1L)
        .name("won")
        .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
        .build();

    Post post = Post.builder()
        .id(postId)
        .title("블로그 포스팅 테스트1")
        .subTitle("보조 제목")
        .content("블로그에 글을 적는 건 즐거워")
        .thumbnail("thumbnail 이미지")
        .author(won)
        .tags("hamill")
        .isPublic(Boolean.TRUE)
        .build();

    given(postRepository.save(post)).willReturn(post);

    //when
    Post result = postRepository.save(post);

    //then
    assertThat(result.getId()).isEqualTo(postId);
  }

  /**
   * 포스트는 1번만 삭제되어야 하고, 같은 포스트는 2번 이상 삭제 될 수 없습니다.
   * 같은 포스트를 여러번 삭제가 되는지 테스트하고,
   * 삭제되지 않을 때 발생하는 Exception을 테스트합니다.
   */
//  @DisplayName("포스트 반복 삭제 테스트")
//  @RepeatedTest(value = 3,name = "{currentRepetition}/{totalRepetitions} 번째 테스트")
//  void delete_third_OnePassTheOthersFailure(RepetitionInfo repetitionInfo) {
//    System.out.println("히히");
//    //given
//    Long postId = 1L;
//
//    //when
//    Post result = postService.delete(postId);
//
//    //then
//
//  }
}
