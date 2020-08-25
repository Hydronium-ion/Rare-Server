package com.codesquad.rare;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RareApplication extends SpringBootServletInitializer {

  private static final String PROPERTIES =
      "spring.config.location="
          +"classpath:/application.yml"
          +",classpath:/application-secret.yml";

  public static void main(String[] args) {
    new SpringApplicationBuilder(RareApplication.class)
        .properties(PROPERTIES)
        .run(args);
  }

}
