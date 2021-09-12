package com.study.realworld.domain.user.error;

import com.study.realworld.global.config.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "Email is Duplication"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String message() {
        return message;
    }

}
