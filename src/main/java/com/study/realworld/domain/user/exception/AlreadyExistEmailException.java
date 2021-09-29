package com.study.realworld.domain.user.exception;

public class AlreadyExistEmailException extends RuntimeException {

    private static final String MESSAGE = "이메일 : [ %s ] 가 이미 존재합니다.";

    public AlreadyExistEmailException(final String email) {
        super(String.format(MESSAGE, email));
    }

}
