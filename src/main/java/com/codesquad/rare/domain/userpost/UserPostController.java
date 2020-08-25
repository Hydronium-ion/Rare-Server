package com.codesquad.rare.domain.userpost;

import static com.codesquad.rare.common.api.ApiResult.OK;
import static com.codesquad.rare.domain.post.Condition.LIKE;

import com.codesquad.rare.common.api.ApiResult;
import com.codesquad.rare.domain.account.AccountRepository;
import com.codesquad.rare.domain.post.PostService;
import com.codesquad.rare.domain.post.response.PostMainResponse;
import com.codesquad.rare.domain.post.response.PostResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserPostController {

  private final PostService postService;
  private final AccountRepository accountRepository;
  private static final String DEFAULT_PAGE = "0";
  private static final String DEFAULT_SIZE = "20";

  @GetMapping("{username}")
  public ApiResult<List<PostResponse>> findAllByLikes(
      @PathVariable("username") String username,
      @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
    return OK(postService.findAllByUsername(username, page, size));
  }

}
