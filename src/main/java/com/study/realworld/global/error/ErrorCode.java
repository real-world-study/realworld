package com.study.realworld.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "Duplicated email exists."),
    USERNAME_DUPLICATION(HttpStatus.CONFLICT, "Duplicated username exists."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "user is not found"),

    INVALID_USERNAME_NULL(HttpStatus.BAD_REQUEST, "username must be provided."),
    INVALID_USERNAME_LENGTH(HttpStatus.BAD_REQUEST, "username length must be less then 20 characters."),
    INVALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, "usernmae must be provided by limited pattern."),

    INVALID_EMAIL_NULL(HttpStatus.BAD_REQUEST, "address must be provided."),
    INVALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, "address must be provided by limited pattern like 'xxx@xxx.xxx'."),

    INVALID_PASSWORD_NULL(HttpStatus.BAD_REQUEST, "password must be provided."),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "password length must be between 6 and 20 characters."),

    PASSWORD_DISMATCH(HttpStatus.FORBIDDEN, "password is dismatch.")
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
