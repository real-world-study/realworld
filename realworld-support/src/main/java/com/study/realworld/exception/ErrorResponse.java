package com.study.realworld.exception;

import lombok.Getter;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> of(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(new ErrorResponse(errorCode));
    }

    private ErrorResponse(final ErrorCode errorCode) {
        timestamp = LocalDateTime.now();
        status = errorCode.getHttpStatus().value();
        error = errorCode.getHttpStatus().name();
        code = errorCode.name();
        message = errorCode.getDescription();
    }
}
