package com.codesquad.rare.domain.post;

import static com.codesquad.rare.common.api.ApiResult.OK;
import static com.codesquad.rare.domain.post.Condition.CREATED;
import static com.codesquad.rare.domain.post.Condition.LIKE;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.response.PostCreateResponse;
import com.codesquad.rare.domain.post.response.PostMainResponse;
import com.codesquad.rare.domain.post.response.PostResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

  private final PostService postService;
  private static final String DEFAULT_PAGE = "0";
  private static final String DEFAULT_SIZE = "20";

  @GetMapping
  public ApiResult<List<PostMainResponse>> findAllByLikes(
      @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
    return OK(postService.findAll(LIKE.getName(), page, size));
  }

  @GetMapping("/recent")
  public ApiResult<List<PostMainResponse>> findAllByCreatedAt(
      @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
    return OK(postService.findAll(CREATED.getName(), page, size));
  }

  @GetMapping("{id}")
  public ApiResult<PostResponse> findById(@PathVariable(value = "id") Long postId) {
    return OK(postService.findById(postId));
  }

  @PostMapping
  public ApiResult<PostCreateResponse> create(
      @Valid @RequestBody PostCreateRequest postCreateRequest) {
    return OK(postService.save(postCreateRequest));
  }

  @DeleteMapping("{id}")
  public ApiResult delete(@PathVariable("id") Long postId) {
    postService.delete(postId);
    return OK(true);
  }
}
