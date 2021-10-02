package com.study.realworld.domain.user.error.exception;

public class UserBusinessException extends RuntimeException {

    public UserBusinessException(final String message) {
        super(message);
    }

}
