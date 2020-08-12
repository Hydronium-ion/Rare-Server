package com.codesquad.rare.domain.account;

import com.codesquad.rare.domain.account.oauth.github.GitHubService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

  private final GitHubService gitHubService;

  @GetMapping("/login/oauth")
  public void create(
      @PathParam("code") @Valid String code, HttpServletResponse response) throws Exception {
    gitHubService.create(code, response);
  }
}
