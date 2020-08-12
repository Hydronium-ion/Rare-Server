package com.codesquad.rare.domain.account;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.domain.account.oauth.github.GitHubService;
import java.net.URI;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

  @GetMapping("oauth")
  public void create(
      @PathParam("code") @Valid String code, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    String jwt = gitHubService.create(code, response);
    response.sendRedirect("http://localhost:8080/posts/likes");

//    response.sendRedirect("http://localhost:3000/login?jwt="+jwt);
//    JwtResponse jwtResponse = new JwtResponse(jwt);
//    return ApiResult.OK(jwtResponse);
//    HttpHeaders headers = new HttpHeaders();
//    headers.setLocation(new URI("http://localhost:3000/login"));

//    Cookie cookie = new Cookie("jwt", jwt);
//    cookie.setMaxAge(24 * 60 * 60);
//    cookie.setPath("/");

//    response.addHeader("jwt", jwt);

//    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }
}
