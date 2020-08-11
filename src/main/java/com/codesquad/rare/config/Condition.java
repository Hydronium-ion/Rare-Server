package com.codesquad.rare.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Condition {

  CREATED("createdAt"), LIKE("likes");

  private String name;
}
