package com.study.realworld.domain.favorite.error;

import com.study.realworld.domain.favorite.error.exception.FavoriteBusinessException;
import com.study.realworld.domain.favorite.error.exception.FavoriteNotFoundException;
import com.study.realworld.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum FavoriteErrorCode implements ErrorCode {

    FAVORITE_NOT_FOUND(FavoriteNotFoundException.class, HttpStatus.BAD_REQUEST, "favorite is not found");

    private final Class exceptionClass;
    private final HttpStatus httpStatus;
    private final String message;

    FavoriteErrorCode(final Class<?> exceptionClass, final HttpStatus httpStatus, final String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static FavoriteErrorCode values(final FavoriteBusinessException exception) {
        final Class<? extends FavoriteBusinessException> exceptionClass = exception.getClass();
        return Arrays.stream(values())
                .filter(favoriteErrorCode -> favoriteErrorCode.exceptionClass.equals(exceptionClass))
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
