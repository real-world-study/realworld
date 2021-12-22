package com.study.realworld.domain.tag.error;

import com.study.realworld.domain.tag.error.exception.DuplicatedTagNameException;
import com.study.realworld.domain.tag.error.exception.TagBusinessException;
import com.study.realworld.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum TagErrorCode implements ErrorCode {

    TAG_NAME_DUPLICATION(DuplicatedTagNameException.class, HttpStatus.BAD_REQUEST, "TagName is Duplication");

    private final Class exceptionClass;
    private final HttpStatus httpStatus;
    private final String message;

    TagErrorCode(final Class<?> exceptionClass, final HttpStatus httpStatus, final String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static TagErrorCode values(final TagBusinessException exception) {
        final Class<? extends TagBusinessException> exceptionClass = exception.getClass();
        return Arrays.stream(values())
                .filter(tagErrorCode -> tagErrorCode.exceptionClass.equals(exceptionClass))
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
