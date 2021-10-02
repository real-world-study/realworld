package com.study.realworld.global.error.testUtils;

import com.study.realworld.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum TestErrorCode implements ErrorCode {
    TEST(HttpStatus.BAD_REQUEST, "test message");

    private final HttpStatus httpStatus;
    private final String message;

    TestErrorCode(final HttpStatus httpStatus, final String message) {
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
