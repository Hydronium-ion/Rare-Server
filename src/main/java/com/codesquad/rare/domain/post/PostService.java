package com.codesquad.rare.domain.post;

import com.codesquad.rare.error.exeception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Transactional(readOnly = true)
  public List<Post> findAll() {
    return postRepository.findAllByOrderByCreatedTimeAtDesc();
  }

  @Transactional(readOnly = true)
  public Post findById(Long postId) {
    return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(Post.class, postId));
  }

  @Transactional
  public Post save(Post post) {
    return postRepository.save(post);
  }
}
