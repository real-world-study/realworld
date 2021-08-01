package com.tistory.povia.realworld.user.exception;

public class DuplicatedEmailException extends RuntimeException{

  private static final String EXCEPTION_MESSAGE = "Duplicated Email found";

  public DuplicatedEmailException(){
    this(EXCEPTION_MESSAGE);
  }

  public DuplicatedEmailException(String message) {
    super(message);
  }
}
