package com.study.realworld.user.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * @author Jeongjoon Seo
 */
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRegisterRequest {

    @JsonProperty(value = "username")
    private String userName;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "password")
    private String password;

    public UserRegisterModel toModel() {
        return new UserRegisterModel(userName, email, password);
    }
}
