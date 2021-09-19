package com.study.realworld.global.jwt.error.exception;

import org.springframework.security.access.AccessDeniedException;

public class JwtAccessDeniedException extends AccessDeniedException {

    public JwtAccessDeniedException(final String message) {
        super(message);
    }

}
