package com.study.realworld.domain.favorite.api;

import com.study.realworld.domain.favorite.error.FavoriteErrorCode;
import com.study.realworld.domain.favorite.error.FavoriteErrorResponse;
import com.study.realworld.domain.favorite.error.exception.FavoriteBusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FavoriteApiAdvice {

    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler(FavoriteBusinessException.class)
    protected ResponseEntity<FavoriteErrorResponse> handleFavoriteBusinessException(final FavoriteBusinessException favoriteBusinessException) {

        log.error("FavoriteBusinessException: {}", favoriteBusinessException.getMessage());

        final FavoriteErrorCode favoriteErrorCode = FavoriteErrorCode.values(favoriteBusinessException);
        final FavoriteErrorResponse favoriteErrorResponse = FavoriteErrorResponse.from(favoriteErrorCode);
        return favoriteErrorResponse.toResponseEntity();
    }
}
