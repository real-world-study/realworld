package com.study.realworld.global.security.error.exception;

public class UserDetailsNullPointerException extends SecurityBusinessException {

    private static final String USER_DETAILS_NULL_MESSAGE = "UserDetails is null";

    public UserDetailsNullPointerException() {
        super(String.format(USER_DETAILS_NULL_MESSAGE));
    }

}
