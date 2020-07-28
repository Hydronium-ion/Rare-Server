package com.codesquad.rare.domain.post;

import static com.codesquad.rare.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.codesquad.rare.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.error.exeception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = {PostController.class})
@AutoConfigureRestDocs
class PostControllerTest {

  private final Logger log = LoggerFactory.getLogger(PostControllerTest.class);

  @Autowired
  MockMvc mockMvc;

  @MockBean
  PostService postService;

  @MockBean
  PostRepository postRepository;

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

    Account won = Account.builder()
        .id(1L)
        .name("won")
        .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
        .build();

    Post post1 = Post.builder()
        .id(1L)
        .title("1번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .likes(random.nextInt(99))
        .tags("1번")
        .views(random.nextInt(999))
        .createdAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Post post2 = Post.builder()
        .id(2L)
        .title("2번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .likes(random.nextInt(99))
        .tags("2번")
        .views(random.nextInt(999))
        .createdAt(LocalDateTime.now())
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
            getDocumentRequest(),
            getDocumentResponse(),
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
                    .type(JsonFieldType.OBJECT),
                fieldWithPath("response.[].views")
                    .description("포스트 조회")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].likes")
                    .description("포스트 좋아요 수")
                    .type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].tags")
                    .description("포스트 태그")
                    .type(JsonFieldType.STRING),
                fieldWithPath("response.[].createdAt")
                    .description("포스트 생성 시간")
                    .type(JsonFieldType.STRING)
            )));
  }

  @DisplayName("포스트 생성")
  @Test
  public void create_post_spring_rest_docs() throws Exception {
    //given
    Account won = Account.builder()
        .id(1L)
        .name("won")
        .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
        .build();

    PostRequestDto postRequestDto = PostRequestDto.builder()
        .title("1번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .tags("1번")
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Post post = Post.from(postRequestDto);
    given(postService.save(postRequestDto)).willReturn(post);

    //when
    ResultActions result =
        this.mockMvc.perform(post("/posts")
            .content(asJsonString(postRequestDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    //then
    result.andDo(document("{class-name}/{method-name}",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("title").description("포스트 제목").type(JsonFieldType.STRING),
            fieldWithPath("content").description("포스트 내용").type(JsonFieldType.STRING),
            fieldWithPath("thumbnail").description("포스트 썸네일 이미지").type(JsonFieldType.STRING),
            fieldWithPath("author").description("포스트 작성자").type(JsonFieldType.OBJECT),
            fieldWithPath("author.id").description("포스트 작성자의 ID").type(JsonFieldType.NUMBER),
            fieldWithPath("author.name").description("포스트 작성자의 이름").type(JsonFieldType.STRING),
            fieldWithPath("author.avatarUrl").description("포스트 작성자의 아바타 URL").type(JsonFieldType.STRING),
            fieldWithPath("tags").description("포스트 태그").type(JsonFieldType.STRING)
        ),
        responseFields(
            fieldWithPath("success").description("성공 유무").type(JsonFieldType.BOOLEAN),
            fieldWithPath("response").description("응답 메세지").type(JsonFieldType.STRING),
            fieldWithPath("error").description("HTTP 상태 코드").type(JsonFieldType.NULL)
        )
    ));
   }

  @DisplayName("포스트 삭제")
  @Test
  public void delete_post_spring_rest_docs() throws Exception {
    //given
    Account won = Account.builder()
        .id(1L)
        .name("won")
        .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
        .build();

    Post post = Post.builder()
        .id(1L)
        .title("1번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .author(won)
        .views(0)
        .likes(0)
        .tags("1번")
        .createdAt(LocalDateTime.now())
        .build();

    postRepository.save(post);
    given(postService.delete(post.getId())).willReturn(post);

    //when
    ResultActions result = this.mockMvc.perform(delete("/posts/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    //then
    result.andDo(document("{class-name}/{method-name}",
        getDocumentRequest(),
        getDocumentResponse(),
        pathParameters(
            parameterWithName("id").description("삭제할 포스트의 ID")
        ),
        responseFields(
            fieldWithPath("success").description("성공 유무").type(JsonFieldType.BOOLEAN),
            fieldWithPath("response").description("응답 메세지").type(JsonFieldType.STRING),
            fieldWithPath("error").description("HTTP 상태 코드").type(JsonFieldType.NULL)
        )
    ));
  }

  @DisplayName("포스트를 좋아요 수를 기준으로 내림차순 출력")
  @Test
  public void find_posts_by_likes_in_descending_order() throws Exception {
    //given
    Random random = new Random();

    Account won = Account.builder()
        .id(1L)
        .name("won")
        .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
        .build();

    Post post1 = Post.builder()
        .id(1L)
        .title("1번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .likes(15)
        .tags("1번")
        .views(random.nextInt(999))
        .createdAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Post post2 = Post.builder()
        .id(2L)
        .title("2번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .likes(20)
        .tags("2번")
        .views(random.nextInt(999))
        .createdAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Post post3 = Post.builder()
        .id(2L)
        .title("2번째 포스팅 입니다")
        .content("이런 저런 내용이 담겨있어요")
        .author(won)
        .likes(30)
        .tags("2번")
        .views(random.nextInt(999))
        .createdAt(LocalDateTime.now())
        .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
        .build();

    Integer page = 0;
    Integer size = 20;

    List<Post> posts = Arrays.asList(post3, post2, post1);
    given(postService.findPostsByLikesInDescendingOrder(page, size)).willReturn(posts);

    //when
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("page", page.toString());
    map.add("size", size.toString());

    ResultActions result = this.mockMvc.perform(get("/posts/likes")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .queryParams(map));

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
                fieldWithPath("error").description("에러 여부(발생 시, 어떠한 에러인지 기재)").type(JsonFieldType.NULL),
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].id").description("포스트 ID 번호(고유한 값)").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].title").description("포스트 제목").type(JsonFieldType.STRING),
                fieldWithPath("response.[].content").description("포스트 내용").type(JsonFieldType.STRING),
                fieldWithPath("response.[].thumbnail").description("포스트 썸네일").type(JsonFieldType.STRING),
                fieldWithPath("response.[].author").description("포스트 저자").type(JsonFieldType.OBJECT),
                fieldWithPath("response.[].views").description("포스트 조회").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].likes").description("포스트 좋아요 수").type(JsonFieldType.NUMBER),
                fieldWithPath("response.[].tags").description("포스트 태그").type(JsonFieldType.STRING),
                fieldWithPath("response.[].createdAt").description("포스트 생성 시간").type(JsonFieldType.STRING)
            )));
  }
}
