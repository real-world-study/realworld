package com.study.realworld.global.security.error.exception;

public class SecurityErrorCodeNullPointerException extends SecurityBusinessException {

    private static final String USER_DETAILS_NULL_MESSAGE = "SecurityErrorCode is null";

    public SecurityErrorCodeNullPointerException() {
        super(String.format(USER_DETAILS_NULL_MESSAGE));
    }

}
