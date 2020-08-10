package com.codesquad.rare.error.exeception;

public class NotFoundException extends ServiceRuntimeException {

  public NotFoundException(Class cls, Object values) {
    super("ID: "+values + ", " + cls.getSimpleName() + "을(를) 찾을 수 없습니다.");
  }
}
