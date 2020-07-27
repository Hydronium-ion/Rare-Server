package com.codesquad.rare.domain.post;


import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest
class PostControllerTest {

  private final Logger log = LoggerFactory.getLogger(PostControllerTest.class);

  @Autowired
  private WebApplicationContext context;

  @Autowired
  MockMvc mockMvc;

  @MockBean
  PostService postService;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withResponseDefaults(prettyPrint())
        )
        .build();
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @DisplayName("메인 페이지 조회 ")
  @Test
  void find_all_posts() throws Exception {

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
            responseFields(
                fieldWithPath("success")
                    .description("성공 여부")
                    .type(JsonFieldType.BOOLEAN),
                fieldWithPath("error")
                    .description("에러 여부(발생 시, 어떠한 에러인지 기재)")
                    .type(JsonFieldType.NULL),
                subsectionWithPath("response")
                    .description("응답"),
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
                    .description("포스트 생성 시간")
                    .type(JsonFieldType.STRING)
            )));
  }

  @DisplayName("포스트 생성")
  @Test
  public void create_post_spring_rest_docs() throws Exception {
    //given
    Random random = new Random();
    PostRequestDto postRequestDto = PostRequestDto.builder()
        .id(1L)
        .title("1번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author("won")
        .likes(random.nextInt(99))
        .tags("1번")
        .views(random.nextInt(999))
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Post post = Post.from(postRequestDto);
    given(postService.save(postRequestDto)).willReturn(post);

    //when
    ResultActions result =
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
            .content(asJsonString(postRequestDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    //then
    result.andDo(document("{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        requestFields(
            fieldWithPath("id").description("포스트 ID 번호").type(JsonFieldType.NUMBER),
            fieldWithPath("title").description("포스트 제목").type(JsonFieldType.STRING),
            fieldWithPath("content").description("포스트 내용").type(JsonFieldType.STRING),
            fieldWithPath("thumbnail").description("포스트 썸네일 이미지").type(JsonFieldType.STRING),
            fieldWithPath("author").description("포스트 작성자").type(JsonFieldType.STRING),
            fieldWithPath("views").description("포스트 조회수").type(JsonFieldType.NUMBER),
            fieldWithPath("likes").description("포스트 좋아요 수").type(JsonFieldType.NUMBER),
            fieldWithPath("tags").description("포스트 태그").type(JsonFieldType.STRING)
        ),
        responseFields(
            fieldWithPath("success").description("성공 유무").type(JsonFieldType.BOOLEAN),
            fieldWithPath("response").description("응답 메세지").type(JsonFieldType.STRING),
            fieldWithPath("error").description("HTTP 상태 코드").type(JsonFieldType.NULL)
        )
    ));
   }
}
