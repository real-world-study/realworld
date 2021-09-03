package com.study.realworld.exception;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import lombok.Getter;

import org.springframework.http.ResponseEntity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("errors")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Getter
public class ErrorResponse {

    private List<String> body;

    public static ResponseEntity<ErrorResponse> of(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(new ErrorResponse(errorCode));
    }

    private ErrorResponse(final ErrorCode errorCode) {
        body = List.of(errorCode.getDescription());
    }
}
