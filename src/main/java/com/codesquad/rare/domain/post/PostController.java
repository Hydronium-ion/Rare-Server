package com.codesquad.rare.domain.post;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.response.PostCreateResponse;
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

  //생성시간 순으로 내림차순 정렬
  @GetMapping("createdAt")
  public ApiResult<List<Post>> findAllInLatestOrder(
      @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
    return OK(postService.findAllAndOrderByCreatedAtDesc(page, size));
  }

  // 좋아요 순으로 내림차순 정렬
  @GetMapping("likes")
  public ApiResult<List<Post>> findAllByLikesInDescendingOrder(
      @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
    return OK(postService.findAllByLikesInDescendingOrder(page, size));
  }

  @GetMapping("{id}")
  public ApiResult<Post> findById(@PathVariable(value = "id") Long postId) {
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
