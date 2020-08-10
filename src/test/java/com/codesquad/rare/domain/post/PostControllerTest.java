package com.codesquad.rare.domain.post;

import static com.codesquad.rare.common.api.ApiResult.OK;
import static com.codesquad.rare.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.codesquad.rare.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.response.PostCreateResponse;
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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = {PostController.class})
@AutoConfigureRestDocs
class PostControllerTest {

  final Logger log = LoggerFactory.getLogger(PostControllerTest.class);

  @Autowired
  MockMvc mockMvc;

  @MockBean
  PostController postController;

  Random random = new Random();

  Account won = Account.builder()
      .id(1L)
      .name("won")
      .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
      .build();


  @BeforeEach
  void set_up(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private PostCreateRequest getPostCreateRequest() {
    return PostCreateRequest.builder()
        .title("타이틀입니다일 (필수)")
        .subTitle("보조 타이틀 입니다(필수)")
        .content("내용")
        .authorId(1L)
        .tags("1번")
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .isPublic(true)
        .build();
  }

  private Post getPost(Random random, Account won, long id, String title) {
    return Post.builder()
        .id(id)
        .title(id + title)
        .subTitle("보조 제목")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .likes(random.nextInt(99))
        .tags("태")
        .views(random.nextInt(999))
        .createdAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .isPublic(true)
        .build();
  }


  @DisplayName("메인 페이지 조회 (생성 시간 기준 내림차순 정렬)")
  @Test
  void find_all_in_latest_order() throws Exception {

    //given
    Post post1 = getPost(random, won, 1L, "1번째 포스팅 입니다");
    Post post2 = getPost(random, won, 2L, "2번째 포스팅 입니다");

    int page = 0;
    int size = 20;

    List<Post> posts = Arrays.asList(post1, post2);
    given(postController.findAllInLatestOrder(page, size)).willReturn(OK(posts));

    //when
    ResultActions result = mockMvc.perform(get("/posts/createdAt")
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", String.valueOf(page))
        .param("size", String.valueOf(size)));

    //then
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            getDocumentRequest(),
            getDocumentResponse(),
            requestParameters(
                parameterWithName("page").description("페이지 넘버"),
                parameterWithName("size").description("페이지 당 보여줄 포스트 수")
            ),

            responseFields(
                fieldWithPath("success").description("성공 여부").type(JsonFieldType.BOOLEAN),
                fieldWithPath("error").description("에러 메세지").type(JsonFieldType.NULL),
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].id").description("포스트 ID 번호(고유한 값)").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].title").description("포스트 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.[].subTitle").description("포스트 보조 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.[].content").description("포스트 내용").type(JsonFieldType.STRING),
                fieldWithPath("response.[].thumbnail").description("포스트 썸네일").type(JsonFieldType.STRING),
                fieldWithPath("response.[].author").description("포스트 저자").type(JsonFieldType.OBJECT).optional(),
                fieldWithPath("response.[].views").description("포스트 조회").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].likes").description("포스트 좋아요 수").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].tags").description("포스트 태그").type(JsonFieldType.STRING),
                fieldWithPath("response.[].createdAt").description("포스트 생성 시간").type(JsonFieldType.STRING)
            )));
  }

  @DisplayName("메인 페이지 조회 (좋아요 순으로 내림차순 정렬)")
  @Test
  public void find_all_by_likes_in_descending_order() throws Exception {
    //given
    Post post1 = getPost(random, won, 1L, "1번째 포스팅 입니다");
    Post post2 = getPost(random, won, 2L, "2번째 포스팅 입니다");
    Post post3 = getPost(random, won, 3L, "3번째 포스팅 입니다");

    int page = 0;
    int size = 20;

    List<Post> posts = Arrays.asList(post3, post2, post1);
    given(postController.findAllByLikesInDescendingOrder(page, size)).willReturn(OK(posts));

    //when
    ResultActions result = mockMvc.perform(get("/posts/likes")
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", String.valueOf(page))
        .param("size", String.valueOf(size)));

    //then
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            getDocumentRequest(),
            getDocumentResponse(),
            requestParameters(
                parameterWithName("page").description("페이지 넘버"),
                parameterWithName("size").description("페이지 당 보여줄 포스트 수")
            ),
            responseFields(
                fieldWithPath("success").description("성공 여부").type(JsonFieldType.BOOLEAN),
                fieldWithPath("error").description("에러 메세지").type(JsonFieldType.NULL),
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].id").description("포스트 ID 번호(고유한 값)").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].title").description("포스트 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.[].subTitle").description("포스트 보조 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.[].content").description("포스트 내용").type(JsonFieldType.STRING),
                fieldWithPath("response.[].thumbnail").description("포스트 썸네일").type(JsonFieldType.STRING),
                fieldWithPath("response.[].author").description("포스트 저자").type(JsonFieldType.OBJECT).optional(),
                fieldWithPath("response.[].views").description("포스트 조회").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].likes").description("포스트 좋아요 수").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].tags").description("포스트 태그").type(JsonFieldType.STRING),
                fieldWithPath("response.[].createdAt").description("포스트 생성 시간").type(JsonFieldType.STRING)
            )));
  }

  @DisplayName("포스트 생성")
  @Test
  void create_post() throws Exception {
    //given
    PostCreateRequest postCreateRequest = getPostCreateRequest();

    PostCreateResponse response = new PostCreateResponse();
    response.setPostId(1L);
    given(postController.create(any(PostCreateRequest.class))).willReturn(OK(response));


    //when
    ResultActions result = mockMvc.perform(post("/posts")
        .content(asJsonString(postCreateRequest))
        .characterEncoding("UTF-8")
        .contentType(MediaType.APPLICATION_JSON));

    //then
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("{class-name}/{method-name}",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("title").description("포스트 제목 (필수)").type(JsonFieldType.STRING),
                    fieldWithPath("subTitle").description("포스트 보조 제목 (필수)").type(JsonFieldType.STRING),
                    fieldWithPath("content").description("포스트 내용").type(JsonFieldType.STRING),
                    fieldWithPath("thumbnail").description("포스트 썸네일 이미지")
                        .type(JsonFieldType.STRING),
                    fieldWithPath("authorId").description("포스트 작성자 ID (필수)")
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("tags").description("포스트 태그").type(JsonFieldType.STRING),
                    fieldWithPath("isPublic").description("포스트 공개 여부 (필수)")
                        .type(JsonFieldType.BOOLEAN)
                ),
                responseFields(
                    fieldWithPath("success").description("성공 유무").type(JsonFieldType.BOOLEAN),
                    fieldWithPath("response").description("응답").type(JsonFieldType.OBJECT),
                    fieldWithPath("response.postId").description("생성된 포스트 ID")
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("error").description("에러 메세지").type(JsonFieldType.NULL)
                )
            ));
  }

  @DisplayName("포스트 삭제")
  @Test
  void delete_post() throws Exception {

    //given
    given(postController.delete(1L)).willReturn(OK(true));

    //when
    ResultActions result = mockMvc.perform(delete("/posts/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON));

    //then
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("포스트 ID")
            ),
            responseFields(
                fieldWithPath("success").description("성공 유무").type(JsonFieldType.BOOLEAN),
                fieldWithPath("response").description("응답").type(JsonFieldType.BOOLEAN),
                fieldWithPath("error").description("에러 메세지").type(JsonFieldType.NULL)
            )
        ));
  }

  @DisplayName("포스트 조회")
  @Test
  void find_post_by_id() throws Exception {

    //given
    Post post = getPost(random, won, 1L, "1번째 포스팅 입니다");

    given(postController.findById(1L)).willReturn(OK(post));

    //when
    ResultActions result = mockMvc.perform(get("/posts/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON));

    //then
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("포스트 ID")
            ),
            responseFields(
                fieldWithPath("success").description("성공 여부").type(JsonFieldType.BOOLEAN),
                fieldWithPath("error").description("에러 메세지").type(JsonFieldType.NULL),
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.id").description("포스트 ID 번호(고유한 값)")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("response.title").description("포스트 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.subTitle").description("포스트 보조 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.content").description("포스트 내용").type(JsonFieldType.STRING),
                fieldWithPath("response.thumbnail").description("포스트 썸네일")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.author").description("포스트 저자").type(JsonFieldType.OBJECT).optional(),
                fieldWithPath("response.views").description("포스트 조회").type(JsonFieldType.NUMBER),
                fieldWithPath("response.likes").description("포스트 좋아요 수").type(JsonFieldType.NUMBER),
                fieldWithPath("response.tags").description("포스트 태그").type(JsonFieldType.STRING),
                fieldWithPath("response.createdAt").description("포스트 생성 시간")
                    .type(JsonFieldType.STRING)
            )));
  }
}
