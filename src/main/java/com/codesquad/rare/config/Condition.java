package com.codesquad.rare.config;

import lombok.Getter;

@Getter
public enum Condition {

  CREATED("createdAt"), LIKE("likes");

  private String name;

  Condition(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
