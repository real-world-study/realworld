package com.study.realworld.domain.follow.error;

import com.study.realworld.domain.follow.error.exception.DuplicatedFollowException;
import com.study.realworld.domain.follow.error.exception.FollowBusinessException;
import com.study.realworld.domain.follow.error.exception.FollowNotFoundException;
import com.study.realworld.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum FollowErrorCode implements ErrorCode {

    FOLLOW_NOT_FOUND(FollowNotFoundException.class, HttpStatus.BAD_REQUEST, "Follow is not exist"),
    DUPLICATED_FOLLOW(DuplicatedFollowException.class, HttpStatus.BAD_REQUEST, "Follow is already exist");

    private final Class exceptionClass;
    private final HttpStatus httpStatus;
    private final String message;

    FollowErrorCode(final Class<?> exceptionClass, final HttpStatus httpStatus, final String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static FollowErrorCode values(final FollowBusinessException exception) {
        final Class<? extends FollowBusinessException> exceptionClass = exception.getClass();
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
