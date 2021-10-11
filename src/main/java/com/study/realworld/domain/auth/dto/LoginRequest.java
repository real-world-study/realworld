package com.study.realworld.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.vo.Email;
import com.study.realworld.domain.user.domain.vo.Password;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class LoginRequest {

    @JsonProperty("email")
    private Email email;

    @JsonProperty("password")
    private Password password;

    LoginRequest() {
    }

    LoginRequest(final Email email, final Password password) {
        this.email = email;
        this.password = password;
    }

    public final Email email() {
        return email;
    }

    public final Password password() {
        return password;
    }

}
