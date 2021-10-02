package com.study.realworld.global.jwt.error.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(final String message) {
        super(message);
    }

}
