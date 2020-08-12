package com.codesquad.rare.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

  private static final String HEADER_AUTH = "Authorization";
  private final JwtService jwtService;

  public JwtInterceptor(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String token = request.getHeader(HEADER_AUTH);

    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    if (token != null && jwtService.checkJwt(token)) {
      return true;
    } else {
      response.sendError(401);
      log.info("##### 로그인을 먼저 해주세요");
      return false;
    }
  }
}
