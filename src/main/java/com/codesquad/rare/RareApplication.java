package com.codesquad.rare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"test"} , exclude = JpaRepositoriesAutoConfiguration.class)
@EnableTransactionManagement
public class RareApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(RareApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(RareApplication.class);
  }

}
