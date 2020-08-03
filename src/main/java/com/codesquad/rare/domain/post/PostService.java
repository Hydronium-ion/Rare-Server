package com.codesquad.rare.domain.post;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.account.AccountRepository;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.response.PostCreateResponse;
import com.codesquad.rare.error.exeception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

  private final PostRepository postRepository;

  private final AccountRepository accountRepository;

  public List<Post> findAllAndOrderByCreatedAtDesc() {
    return postRepository.findAllByOrderByCreatedAtDesc();
  }

  public Post findById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
  }

  @Transactional
  public PostCreateResponse save(PostCreateRequest postCreateRequest) {
    Account author = accountRepository.findById(postCreateRequest.getAuthorId())
        .orElseThrow(() -> new NotFoundException(Account.class, postCreateRequest.getAuthorId()));
    Post savedPost = postRepository.save(Post.toEntity(postCreateRequest, author));
    PostCreateResponse response = new PostCreateResponse();
    response.setPostId(savedPost.getId());
    return response;
  }

  @Transactional
  public Post delete(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
    postRepository.delete(post);
    return post;
  }

  public List<Post> findAllByLikesInDescendingOrder(Integer page, Integer size) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("likes").descending());
    return postRepository.findAll(pageRequest).getContent();
  }
}
