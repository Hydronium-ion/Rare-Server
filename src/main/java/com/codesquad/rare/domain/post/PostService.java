package com.codesquad.rare.domain.post;

import com.codesquad.rare.error.exeception.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<Post> findAll() {
    return postRepository.findAllByOrderByCreatedAtDesc();
  }

  public Post findById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
  }

  @Transactional
  public Post save(PostRequestDto postRequestDto) {
    Post post = Post.from(postRequestDto);
    return postRepository.save(post);
  }

  @Transactional
  public Post delete(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
    postRepository.delete(post);
    return post;
  }

  public List<Post> findPostsByLikesInDescendingOrder(Integer page, Integer size) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("likes").descending());
    return postRepository.findAll(pageRequest).getContent();
  }
}
