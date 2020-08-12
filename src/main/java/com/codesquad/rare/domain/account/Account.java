package com.codesquad.rare.domain.account;

import com.codesquad.rare.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseTimeEntity {

  @Id
  @Column(name = "account_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;

  private String avatarUrl;

  private boolean isDelete;

  @Builder
  public Account(final String name, final String email, final String avatarUrl, final boolean isDelete) {
    this.name = name;
    this.email = email;
    this.avatarUrl = avatarUrl;
    this.isDelete = isDelete;
  }

  public static Account from(ResponseEntity<Map> AccountCreateMap) {
    return Account.builder()
        .email(AccountCreateMap.getBody().get("email").toString())
        .name(AccountCreateMap.getBody().get("name").toString())
        .avatarUrl(AccountCreateMap.getBody().get("avatar_url").toString())
        .isDelete(true)
        .build();
  }
}
