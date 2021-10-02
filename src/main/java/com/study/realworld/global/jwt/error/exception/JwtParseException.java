package com.study.realworld.global.jwt.error.exception;

public class JwtParseException extends JwtAuthenticationException {

    private static final String MESSAGE = "Jwt parsing failed.";

    public JwtParseException() {
        super(MESSAGE);
    }

}
