package com.study.realworld.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Parameter"),

    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "Account information does not exist."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "This user already exists.")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
