package com.codesquad.rare.domain.account;

import static com.codesquad.rare.common.api.ApiResult.OK;
import static com.codesquad.rare.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.codesquad.rare.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@WebMvcTest(controllers = {AccountController.class})
@AutoConfigureRestDocs
class AccountControllerTest {

  final Logger log = LoggerFactory.getLogger(AccountControllerTest.class);

  @Autowired
  MockMvc mockMvc;

  @MockBean
  AccountController accountController;

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

  @DisplayName("유저 삭제")
  @Test
  public void delete_account() throws Exception {
    //given
    given(accountController.delete(1L)).willReturn(OK(new AccountDeleteResponse(1L)));

    //when
    ResultActions result = this.mockMvc.perform(delete("/users/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON));

    //then
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("id").description("유저 ID")
            ),
            responseFields(
                fieldWithPath("success").description("성공 유무").type(JsonFieldType.BOOLEAN),
                fieldWithPath("response").description("응답").type(JsonFieldType.OBJECT),
                fieldWithPath("response.accountId").description("유저 ID").type(JsonFieldType.NUMBER),
                fieldWithPath("error").description("에러 메세지").type(JsonFieldType.NULL)
            )
        ));
  }
}