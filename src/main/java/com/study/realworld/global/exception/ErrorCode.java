package com.study.realworld.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // user
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "user is not found"),

    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "Duplicated email exists."),
    USERNAME_DUPLICATION(HttpStatus.CONFLICT, "Duplicated username exists."),

    PASSWORD_DISMATCH(HttpStatus.FORBIDDEN, "password is dismatch."),

    INVALID_USERNAME_NULL(HttpStatus.BAD_REQUEST, "username must be provided."),
    INVALID_USERNAME_LENGTH(HttpStatus.BAD_REQUEST, "username length must be less then 20 characters."),
    INVALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, "usernmae must be provided by limited pattern."),
    INVALID_EMAIL_NULL(HttpStatus.BAD_REQUEST, "address must be provided."),
    INVALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, "address must be provided by limited pattern like 'xxx@xxx.xxx'."),
    INVALID_PASSWORD_NULL(HttpStatus.BAD_REQUEST, "password must be provided."),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "password length must be between 6 and 20 characters."),

    INVALID_FOLLOW(HttpStatus.BAD_REQUEST, "follow must be provided by unfollowed user"),
    INVALID_UNFOLLOW(HttpStatus.BAD_REQUEST, "unfollow must be provided by followed user"),

    // authentication
    INVALID_EXPIRED_JWT(HttpStatus.BAD_REQUEST, "this jwt has expired."),
    INVALID_MALFORMED_JWT(HttpStatus.BAD_REQUEST, "this jwt was malformed."),
    INVALID_UNSUPPORTED_JWT(HttpStatus.BAD_REQUEST, "this jwt wat not supported."),
    INVALID_ILLEGAL_ARGUMENT_JWT(HttpStatus.BAD_REQUEST, "this jwt was wrong."),

    // article
    INVALID_TITLE_NULL(HttpStatus.BAD_REQUEST, "title must be provided"),
    INVALID_TITLE_LENGTH(HttpStatus.BAD_REQUEST, "title length must by less than 20 characters."),
    INVALID_SLUG_NULL(HttpStatus.BAD_REQUEST, "slug must be provided"),
    INVALID_SLUG_LENGTH(HttpStatus.BAD_REQUEST, "slug length must by less than 20 characters."),

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

    @Override
    public String toString() {
        return message;
    }

}
