package com.codesquad.rare.domain.post;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.response.PostCreateResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

  private final PostService postService;

  @GetMapping
  public ApiResult<List<Post>> findAllInLatestOrder() {
    return OK(postService.findAllAndOrderByCreatedAtDesc());
  }

  @GetMapping("{id}")
  public ApiResult<Post> findById(@PathVariable(value = "id") Long postId) {
    return OK(postService.findById(postId));
  }

  @PostMapping
  public ApiResult<PostCreateResponse> create(@RequestBody PostCreateRequest postCreateRequest) {
    return OK(postService.save(postCreateRequest));
  }

  @DeleteMapping("/{id}")
  public ApiResult delete(@PathVariable("id") Long postId) {
    postService.delete(postId);
    return OK(true);
  }
}
