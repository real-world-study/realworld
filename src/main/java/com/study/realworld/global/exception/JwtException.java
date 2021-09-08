package com.study.realworld.global.exception;

import org.springframework.http.HttpStatus;

public class JwtException extends RuntimeException {

    private ErrorCode errorCode;

    public JwtException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public int getHttpStatusValue() {
        return getHttpStatus().value();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

}
