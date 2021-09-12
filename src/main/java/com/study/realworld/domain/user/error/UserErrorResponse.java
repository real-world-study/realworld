package com.study.realworld.domain.user.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.global.config.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@JsonTypeName("errors")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserErrorResponse {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonProperty("body")
    private List<String> body;

    UserErrorResponse() {
    }

    public UserErrorResponse(final ErrorCode errorCode) {
        validateNull(errorCode);
        httpStatus = errorCode.httpStatus();
        body = List.of(errorCode.message());
    }

    private void validateNull(final ErrorCode errorCode) {
        if(Objects.isNull(errorCode)) {
            throw new IllegalArgumentException();
        }
    }

    public ResponseEntity<UserErrorResponse> toResponseEntity() {
        return ResponseEntity.status(this.httpStatus).body(this);
    }

}
