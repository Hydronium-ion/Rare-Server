package com.codesquad.rare.domain.post;

import com.codesquad.rare.error.exeception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<Post> findAll() {
    return postRepository.findAllByOrderByCreatedTimeAtDesc();
  }

  public Post findById(Long postId) {
    return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(Post.class, postId));
  }

  @Transactional
  public void save(PostRequestDto postRequestDto) {
    Post post = Post.builder()
        .id(postRequestDto.getId())
        .title(postRequestDto.getTitle())
        .content(postRequestDto.getContent())
        .thumbnail(postRequestDto.getThumbnail())
        .author(postRequestDto.getAuthor())
        .views(postRequestDto.getViews())
        .likes(postRequestDto.getLikes())
        .tags(postRequestDto.getTags())
        .createdTimeAt(LocalDateTime.now())
        .build();

    postRepository.save(post);
  }

  @Transactional
  public void delete(Long postId) {
    Post post = postRepository.getOne(postId);
    postRepository.delete(post);
  }
}
