package com.codesquad.rare.error.exeception;

public class FilenameExtensionException extends RuntimeException {

  public FilenameExtensionException() { super("This file type is not supported"); }
}
