package com.study.realworld.domain.user.error.exception;

public final class PasswordMissMatchException extends UserBusinessException {

    private static final String MESSAGE = "패스워드가 일치하지 않습니다.";

    public PasswordMissMatchException() {
        super(MESSAGE);
    }
}
