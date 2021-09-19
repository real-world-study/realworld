package com.study.realworld.global.security.error;

import com.study.realworld.global.error.ErrorCode;
import com.study.realworld.global.security.error.exception.SecurityBusinessException;
import com.study.realworld.global.security.error.exception.UserDetailsNullPointerException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum SecurityErrorCode implements ErrorCode {
    USER_DETAILS_NULL_POINTER(UserDetailsNullPointerException.class, HttpStatus.BAD_REQUEST, "The request data is invalid");

    private final Class exceptionClass;
    private final HttpStatus httpStatus;
    private final String message;

    SecurityErrorCode(final Class exceptionClass, final HttpStatus httpStatus, final String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static SecurityErrorCode values(final SecurityBusinessException exception) {
        final Class<? extends SecurityBusinessException> exceptionClass = exception.getClass();
        return Arrays.stream(values())
                .filter(userErrorCode -> userErrorCode.exceptionClass.equals(exceptionClass))
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
