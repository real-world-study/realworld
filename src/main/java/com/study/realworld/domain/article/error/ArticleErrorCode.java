package com.study.realworld.domain.article.error;

import com.study.realworld.domain.article.error.exception.ArticleBusinessException;
import com.study.realworld.domain.article.error.exception.AuthorMissMatchException;
import com.study.realworld.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ArticleErrorCode implements ErrorCode {

    AUTHOR_MISS_MATCH(AuthorMissMatchException.class, HttpStatus.BAD_REQUEST, "login user is not author");

    private final Class exceptionClass;
    private final HttpStatus httpStatus;
    private final String message;

    ArticleErrorCode(final Class<?> exceptionClass, final HttpStatus httpStatus, final String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static ArticleErrorCode values(final ArticleBusinessException exception) {
        final Class<? extends ArticleBusinessException> exceptionClass = exception.getClass();
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
