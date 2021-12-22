package com.study.realworld.domain.user.error.exception;

public class PasswordNullOrEmptyException extends UserBusinessException {

    private static final String MESSAGE = "올바르지 않은 패스워드입니다.";

    public PasswordNullOrEmptyException() {
        super(MESSAGE);
    }
}
