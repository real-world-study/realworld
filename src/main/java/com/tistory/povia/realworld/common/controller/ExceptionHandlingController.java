package com.tistory.povia.realworld.common.controller;

import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({
        IllegalArgumentException.class,
        DuplicatedEmailException.class,
        ExpiredJwtException.class
    })
    protected ResponseEntity<?> handleBadRequestException(Exception exception) {
        log.debug("Bad Request: {}", exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    /*
     * 나중에 테스트 추가 후 추가 예정
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> internalErrorException(Exception exception) {
        log.error("Internal Error: {}", exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(Optional.empty());
    }
     */
}
