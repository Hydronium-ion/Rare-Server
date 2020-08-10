package com.codesquad.rare.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {

  @Id
  @Column(name = "account_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String avatarUrl;

  @Builder
  private Account(String name, String avatarUrl) {
    this.name = name;
    this.avatarUrl = avatarUrl;
  }
}
