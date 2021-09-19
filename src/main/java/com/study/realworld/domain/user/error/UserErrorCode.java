package com.study.realworld.domain.user.error;

import com.study.realworld.domain.user.error.exception.*;
import com.study.realworld.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum UserErrorCode implements ErrorCode {

    EMAIL_DUPLICATION(DuplicatedEmailException.class, HttpStatus.BAD_REQUEST, "Email is Duplication"),
    EMAIL_NOT_FOUND(EmailNotFoundException.class, HttpStatus.BAD_REQUEST, "Email is not found"),
    IDENTITY_NOT_FOUND(IdentityNotFoundException.class, HttpStatus.BAD_REQUEST, "Identity is not found"),
    PASSWORD_MISS_MATCH(PasswordMissMatchException.class, HttpStatus.BAD_REQUEST, "Password is miss match")
    ;

    private final Class exceptionClass;
    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(final Class<?> exceptionClass, final HttpStatus httpStatus, final String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static UserErrorCode values(final UserBusinessException exception) {
        final Class<? extends UserBusinessException> exceptionClass = exception.getClass();
        return Arrays.stream(values())
                .filter(userErrorCode ->  userErrorCode.exceptionClass.equals(exceptionClass))
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
