package com.study.realworld.domain.auth.exception;

public class JwtProviderNotSupportTypeException extends RuntimeException {

    private static final String MESSAGE = "[ %s ] is not supported in JwtProvider";

    public JwtProviderNotSupportTypeException(final String message) {
        super(String.format(MESSAGE, message));
    }

}
