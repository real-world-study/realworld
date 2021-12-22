package com.study.realworld.global.jwt.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.global.error.ErrorCode;
import com.study.realworld.global.jwt.error.exception.JwtErrorCodeNullPointerException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@JsonTypeName("errors")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class JwtErrorResponse {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonProperty("body")
    private List<String> body;

    public static JwtErrorResponse from(final ErrorCode errorCode) {
        return new JwtErrorResponse(errorCode.httpStatus(), List.of(errorCode.message()));
    }

    private void validateNull(final ErrorCode errorCode) {
        if (Objects.isNull(errorCode)) {
            throw new JwtErrorCodeNullPointerException();
        }
    }
}