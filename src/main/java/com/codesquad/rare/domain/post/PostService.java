package com.codesquad.rare.domain.post;

import static com.codesquad.rare.domain.post.Condition.CREATED;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.account.AccountRepository;
import com.codesquad.rare.domain.post.request.PostCreateRequest;
import com.codesquad.rare.domain.post.request.PostUpdateRequest;
import com.codesquad.rare.domain.post.response.PostIdResponse;
import com.codesquad.rare.domain.post.response.PostMainResponse;
import com.codesquad.rare.domain.post.response.PostResponse;
import com.codesquad.rare.error.exeception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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

  public List<PostMainResponse> findAll(final String condition, final int page, final int size) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(condition).descending());
    return postRepository.findAllByIsPublicTrue(pageRequest)
        .stream()
        .map(this::toPostMainResponse)
        .collect(Collectors.toList());
  }

  public List<PostResponse> findAllByUsername(final String username, final int page,
      final int size) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(CREATED.getName()).descending());
    Account account = accountRepository.findByName(username);
    return postRepository.findByAuthor(account, pageRequest)
        .stream()
        .map(this::toPostResponse)
        .collect(Collectors.toList());
  }

  public PostResponse findById(final Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
    return toPostResponse(post);
  }

  @Transactional
  public PostIdResponse save(final PostCreateRequest postCreateRequest) {
    Account author = accountRepository.findById(postCreateRequest.getAuthorId())
        .orElseThrow(() -> new NotFoundException(Account.class, postCreateRequest.getAuthorId()));
    Post savedPost = postRepository.save(Post.of(postCreateRequest, author));
    return new PostIdResponse(savedPost.getId());
  }

  @Transactional
  public PostIdResponse update(final PostUpdateRequest postUpdateRequest, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
    post.update(postUpdateRequest);
    Post updatedPost = postRepository.save(post);
    return new PostIdResponse(updatedPost.getId());
  }

  @Transactional
  public PostIdResponse delete(final Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
    postRepository.delete(post);
    return new PostIdResponse(post.getId());
  }

  @Transactional
  public PostIdResponse addCountOfLike(final Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
    post.addLikes();
    postRepository.save(post);
    return new PostIdResponse(post.getId());
  }

  private PostMainResponse toPostMainResponse(final Post post) {
    return PostMainResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .thumbnail(post.getThumbnail())
        .author(post.getAuthor())
        .views(post.getViews())
        .likes(post.getLikes())
        .createdAt(post.getCreatedAt())
        .build();
  }

  private PostResponse toPostResponse(final Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .subTitle(post.getSubTitle())
        .content(post.getContent())
        .thumbnail(post.getThumbnail())
        .author(post.getAuthor())
        .views(post.getViews())
        .likes(post.getLikes())
        .tags(post.getTags())
        .createdAt(post.getCreatedAt())
        .build();
  }
}
