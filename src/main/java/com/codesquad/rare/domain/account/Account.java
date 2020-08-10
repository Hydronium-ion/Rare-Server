package com.codesquad.rare.domain.account;

import com.codesquad.rare.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
public class Account {

  @Id
  @Column(name = "account_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;

  private String avatarUrl;

  @JsonManagedReference
  @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
  private List<Post> posts = new ArrayList<>();

  public static Account toEntity(ResponseEntity<Map> resultMap) {
    return Account.builder()
        .email(resultMap.getBody().get("email").toString())
        .name(resultMap.getBody().get("name").toString())
        .avatarUrl(resultMap.getBody().get("avatar_url").toString())
        .build();
  }
}
