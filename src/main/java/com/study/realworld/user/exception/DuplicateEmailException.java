package com.study.realworld.user.exception;

import com.study.realworld.global.error.exception.BusinessException;

public class DuplicateEmailException extends BusinessException {

    private static final String ERROR_MESSAGE = "Duplicated email exists.";

    public DuplicateEmailException() {
        this(ERROR_MESSAGE);
    }

    public DuplicateEmailException(String message) {
        super(message);
    }

}
