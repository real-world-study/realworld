package com.study.realworld.domain.auth.exception;

public class JwtProviderNotSupportTokenException extends RuntimeException {

    private static final String MESSAGE = "this token is not supported in JwtProvider";

    public JwtProviderNotSupportTokenException() {
        super(MESSAGE);
    }

}
