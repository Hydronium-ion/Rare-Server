package com.codesquad.rare.domain.post;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = {PostController.class})
class PostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  PostService postService;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @DisplayName("메인 페이지 조회")
  @Test
  void 메인_페이지_조회() throws Exception {

    //given
    Random random = new Random();

    Post post1 = Post.builder()
        .id(1L)
        .title("1번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author("won")
        .likes(random.nextInt(99))
        .tags("1번")
        .views(random.nextInt(999))
        .createdTimeAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Post post2 = Post.builder()
        .id(2L)
        .title("2번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author("won")
        .likes(random.nextInt(99))
        .tags("2번")
        .views(random.nextInt(999))
        .createdTimeAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    List<Post> posts = Arrays.asList(post1, post2);
    given(postService.findAll()).willReturn(posts);

    //when
    ResultActions result = mockMvc.perform(get("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    //then
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("success").description("성공 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("error").description("에러 여부(발생 시, 어떠한 에러인지 기재)")
                    .type(JsonFieldType.NULL),
                fieldWithPath("response.[].id")
                    .description("포스트 ID 번호(고유한 값)")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].title")
                    .description("포스트 제목")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.[].content")
                    .description("포스트 내용")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.[].thumbnail")
                    .description("포스트 썸네일")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.[].author")
                    .description("포스트 저자")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.[].views")
                    .description("포스트 조회")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].likes")
                    .description("포스트 좋아요 수")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].tags")
                    .description("포스트 태그")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.[].createdTimeAt")
                    .description("포스트 생성시간")
                    .type(JsonFieldType.STRING)
            )));
  }
}