package com.tistory.povia.realworld.common.controller;

import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionHandlingController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(
    {IllegalArgumentException.class, DuplicatedEmailException.class}
  )
  public ResponseEntity<?> handleBadRequestException(Exception exception){
    log.debug("Bad Request: {}", exception.getMessage(), exception);
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

  @ExceptionHandler(
    {Exception.class, RuntimeException.class}
  )
  public ResponseEntity<?> internalErrorException(Exception exception){
    log.error("Internal Error: {}", exception.getMessage(), exception);
    return ResponseEntity.internalServerError().body(Optional.empty());
  }
}
