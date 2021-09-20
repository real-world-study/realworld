package com.study.realworld.global.jwt.error;

import com.study.realworld.global.error.ErrorCode;
import com.study.realworld.global.jwt.error.exception.JwtAuthenticationException;
import com.study.realworld.global.jwt.error.exception.JwtParseException;
import com.study.realworld.global.jwt.error.exception.JwtProviderNotSupportTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;

public enum JwtErrorCode implements ErrorCode {
    DEFAULT_AUTHENTICATION(InsufficientAuthenticationException.class, HttpStatus.UNAUTHORIZED),
    JWT_AUTHENTICATION(JwtAuthenticationException.class, HttpStatus.UNAUTHORIZED),
    JWT_PROVIDER_NOT_SUPPORT_TYPE(JwtProviderNotSupportTypeException.class, HttpStatus.UNAUTHORIZED),
    JWT_PARSE(JwtParseException.class, HttpStatus.FORBIDDEN);

    private static final String ERROR_CODE_MESSAGE = "Unauthorized";

    private final Class exceptionClass;
    private final HttpStatus httpStatus;

    JwtErrorCode(final Class exceptionClass, final HttpStatus httpStatus) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public static JwtErrorCode values(final AuthenticationException exception) {
        final Class<? extends AuthenticationException> exceptionClass = exception.getClass();
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
        return ERROR_CODE_MESSAGE;
    }

}
