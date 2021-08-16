package com.study.realworld.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "Account information does not exist.")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
