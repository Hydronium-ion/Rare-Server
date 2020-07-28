package com.codesquad.rare.domain.post;

import static com.codesquad.rare.common.api.ApiResult.OK;

import com.codesquad.rare.common.api.ApiResult;
import java.time.LocalDateTime;
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
  public ApiResult<List<Post>> main() {
    return OK(postService.findAll());
  }

  @GetMapping("{id}")
  public ApiResult<Post> findByPostId(@PathVariable(value = "id") Long id) {
    return OK(postService.findById(id));
  }

  //Post 를 생성합니다.
  //method 명은 save, create? 뭐가 좋을까요?
  @PostMapping
  public ApiResult save(@RequestBody PostRequestDto postRequestDto) {
    postService.save(postRequestDto);
    return OK("true");
  }

  //Post 를 삭제합니다.
  @DeleteMapping("/{id}")
  public ApiResult delete(@PathVariable("id") Long postId) {
    postService.delete(postId);
    return OK("true");
  }
}
