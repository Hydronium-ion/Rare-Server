package com.codesquad.rare.domain.post;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

  private final PostService postService;

  @GetMapping
  public ApiResult<List<Post>> main() {
    return OK(postService.findAll());
  }

  @GetMapping("{id}")
  public ApiResult<Post> findByPostId(@PathVariable(value = "id") Long id) {
    return OK(postService.findById(id));
  }
}
