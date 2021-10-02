package com.study.realworld.global.jwt.error.exception;

public class JwtErrorCodeNullPointerException extends JwtAuthenticationException {

    private static final String MESSAGE = "JwtErrorCode is null";

    public JwtErrorCodeNullPointerException() {
        super(MESSAGE);
    }

}
