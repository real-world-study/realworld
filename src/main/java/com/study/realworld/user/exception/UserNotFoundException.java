package com.study.realworld.user.exception;

import com.study.realworld.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String ERROR_MESSAGE = "user is not found.";

    public UserNotFoundException() {
        this(ERROR_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
