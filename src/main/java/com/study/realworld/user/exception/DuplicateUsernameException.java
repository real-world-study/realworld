package com.study.realworld.user.exception;

import com.study.realworld.global.error.exception.BusinessException;

public class DuplicateUsernameException extends BusinessException {

    private static final String ERROR_MESSAGE = "Duplicated username exists.";

    public DuplicateUsernameException() {
        this(ERROR_MESSAGE);
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }

}
