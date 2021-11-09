package com.study.realworld.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Password;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserLoginRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    protected UserLoginRequest() {
    }

    public Email toEmail() {
        return Email.of(email);
    }

    public Password toPassword() {
        return Password.of(password);
    }

}
