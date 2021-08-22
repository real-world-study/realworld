package com.study.realworld.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Parameter"),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "Account information does not exist."),

    WRONG_PASSWORD(HttpStatus.FORBIDDEN, "The user information you wrote is wrong."),

    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "Unauthenticated user."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "This user already exists.")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
