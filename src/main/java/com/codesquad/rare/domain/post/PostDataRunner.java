package com.codesquad.rare.domain.post;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class PostDataRunner implements ApplicationRunner {

  private final PostRepository postRepository;

  public PostDataRunner(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Random random = new Random();
    LocalDateTime localDateTime = LocalDateTime.now();
    long days = localDateTime.until(localDateTime, ChronoUnit.DAYS);
    List<Post> postList = new ArrayList<>();


    for (int i = 1; i <= 10; i++) {
      Post post = Post.builder()
          .title(i + "번째 포스팅 입니다")
          .content("이런 저런 내용이 담겨있어요")
          .author("won")
          .likes(random.nextInt(99))
          .tags(i + "번")
          .views(random.nextInt(999))
          .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
          .createdTimeAt(localDateTime.plusDays(random.nextInt(10 -5 + 1) + 5)) // 5~10사이의 일수를 더한다
          .build();

      postList.add(post);
    }

    save(postList);
    
  }
  @Transactional
  public void save(List<Post> postList) {
    postRepository.saveAll(postList);
  }
}
