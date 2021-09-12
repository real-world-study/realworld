package com.study.realworld.domain.user.error;

import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import com.study.realworld.global.config.error.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum UserErrorCode implements ErrorCode {

    EMAIL_DUPLICATION(DuplicatedEmailException.class, HttpStatus.BAD_REQUEST, "Email is Duplication"),
    ;

    private final Class exception;
    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(final Class<?> exception, final HttpStatus httpStatus, final String message) {
        this.exception = exception;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static UserErrorCode values(final Exception e) {
        return Arrays.stream(values())
                .filter(userErrorCode ->  userErrorCode.exception.equals(e.getClass()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
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
