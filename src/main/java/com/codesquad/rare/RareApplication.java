package com.codesquad.rare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RareApplication {

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
