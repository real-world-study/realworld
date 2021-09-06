package com.study.realworld.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "Duplicated email exists."),
    USERNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "Duplicated username exists."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "user is not found")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}
