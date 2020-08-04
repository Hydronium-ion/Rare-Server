package com.codesquad.rare.domain.account.oauth.github;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GitHubController {

  private final GitHubService gitHubService;

  @GetMapping("/login/oauth")
  public void login(
      @PathParam("code") @Valid String code, HttpServletResponse response) throws IOException {
    gitHubService.login(code, response);
  }
}
