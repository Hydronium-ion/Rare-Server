package com.codesquad.rare.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Condition {

  CREATED("createdAt"), LIKE("likes");

  private String name;
}
