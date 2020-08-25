package com.codesquad.rare.domain.post;

import com.codesquad.rare.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAllByIsPublicTrue(Pageable pageable);

  Page<Post> findByAuthor(Account author, Pageable pageable);
}
