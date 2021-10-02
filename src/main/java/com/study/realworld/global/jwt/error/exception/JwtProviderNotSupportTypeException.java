package com.study.realworld.global.jwt.error.exception;

public class JwtProviderNotSupportTypeException extends JwtAuthenticationException {

    private static final String MESSAGE = "[ %s ] is not supported in JwtProvider";

    public JwtProviderNotSupportTypeException(final String message) {
        super(String.format(MESSAGE, message));
    }

}
