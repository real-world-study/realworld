package com.study.realworld.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException excpetion) {
        log.debug("Bad request exception occurred : {}", excpetion.getMessage(), excpetion);
        return ErrorResponse.from(excpetion, BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception exception) {
        return ErrorResponse.from(exception, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        log.warn("Unexpected service exception occurred: {}", exception.getMessage(), exception);
        return ErrorResponse.of(exception.getErrorCode());
    }

//    TODO
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception) {
//        log.error("Unexpected exception occurred: {}", exception.getMessage(), exception);
//        return ErrorResponse.from(exception, BAD_REQUEST);
//    }

}
