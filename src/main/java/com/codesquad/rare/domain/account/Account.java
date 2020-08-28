package com.codesquad.rare.domain.account;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import lombok.AccessLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {

  @Id
  @Column(name = "account_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;

  private String avatarUrl;

  @Builder
  public Account(final String name, final String email, final String avatarUrl) {
    this.name = name;
    this.email = email;
    this.avatarUrl = avatarUrl;
  }

  public static Account from(ResponseEntity<Map> AccountCreateMap) {
    return Account.builder()
        .email(AccountCreateMap.getBody().get("email").toString())
        .name(AccountCreateMap.getBody().get("name").toString())
        .avatarUrl(AccountCreateMap.getBody().get("avatar_url").toString())
        .build();
  }

  public static Account of(ResponseEntity<Map> AccountCreateMap, String email) {
    return Account.builder()
        .email(email)
        .name(AccountCreateMap.getBody().get("name").toString())
        .avatarUrl(AccountCreateMap.getBody().get("avatar_url").toString())
        .build();
  }
}
