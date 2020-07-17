package com.codesquad.rare.domain.post;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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

    for (int i = 1; i <= 10; i++) {
      Post post = Post.builder()
          .title(i + "번째 포스팅 입니다")
          .content("이런 저런 내용이 담겨있어요")
          .author("won")
          .likes(random.nextInt(99))
          .tags(i + "번")
          .views(random.nextInt(999))
          .thumbnail("https://i.ytimg.com/vi/FN506P8rX4s/maxresdefault.jpg")
          .build();

      postRepository.save(post);
    }
    
  }
}