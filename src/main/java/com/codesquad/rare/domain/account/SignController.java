package com.codesquad.rare.domain.account;

import com.codesquad.rare.domain.account.oauth.github.GitHubService;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
@Slf4j
public class SignController {

  private final GitHubService gitHubService;

  @Value("${redirection.url}")
  private String redirectionUrl;

  @GetMapping("github")
  public void githubLogin(HttpServletResponse response) throws Exception {
    log.info("##### callback before");
    gitHubService.sendRedirect(response);
  }

  @GetMapping("callback")
  public ResponseEntity create(
      @PathParam("code") @Valid String code, HttpServletResponse response) {
    log.info("##### callback after");
    log.info("##### code: {}", code);
    String jwt = gitHubService.create(code);

    Cookie cookie = new Cookie("_rare_service_production", jwt);
    cookie.setMaxAge(60 * 60);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
//    cookie.setSecure(true);        도메인이 HTTPS일 때 다음 설정을 사용하자
//    cookie.setDomain(".rare.io");  도메인을 구매하고 다음 설정을 사용하자
    response.addCookie(cookie);
    response.setHeader("Location", redirectionUrl);

    return new ResponseEntity(HttpStatus.FOUND);
  }
}
