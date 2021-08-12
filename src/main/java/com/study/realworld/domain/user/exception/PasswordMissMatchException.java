package com.study.realworld.domain.user.exception;

public final class PasswordMissMatchException extends RuntimeException {

    private static final String MESSAGE = "password is not match";

    public PasswordMissMatchException() {
        super(MESSAGE);
    }

}
