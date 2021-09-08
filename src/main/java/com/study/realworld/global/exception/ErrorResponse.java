package com.study.realworld.global.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonTypeName("errors")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ErrorResponse {

    @JsonProperty("body")
    private List<String> body;

    protected ErrorResponse() {
    }

    private ErrorResponse(List<String> body) {
        this.body = body;
    }

    public static String toJson(RuntimeException e) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new ErrorResponse(List.of(e.getMessage())));
    }

    public static ResponseEntity<ErrorResponse> of(ErrorCode e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(List.of(e.getMessage())));
    }

    public static ResponseEntity<ErrorResponse> from(Exception e, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(List.of(e.getMessage())));
    }

}
