package com.codesquad.rare.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String TIME_ZONE = "Asia/Seoul";

  private static final String[] EXCLUDE_PATHS = {
//      "/posts/likes",
//      "/posts/createdAt",
//      "/oauth/login",
//      "/login"
  };

  private final JwtInterceptor jwtInterceptor;

  public WebConfig(JwtInterceptor jwtInterceptor) {
    this.jwtInterceptor = jwtInterceptor;
  }

//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(jwtInterceptor)
//        // 토큰이 필요한 API
//        .addPathPatterns("")
//        // 토큰이 필요하지 않은 API 는 배제한다
//        .excludePathPatterns(EXCLUDE_PATHS);
//  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> {
      builder.simpleDateFormat(DATE_TIME_FORMAT);
      builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
      builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
      builder.serializers(new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
      builder.timeZone(TimeZone.getTimeZone(TIME_ZONE));
    };
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTION")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
