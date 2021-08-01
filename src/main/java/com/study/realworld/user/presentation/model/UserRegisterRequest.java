package com.study.realworld.user.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.application.model.UserRegisterModel;

import lombok.AllArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * @author Jeongjoon Seo
 */
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@AllArgsConstructor
public class UserRegisterRequest {

    @JsonProperty(value = "username")
    private final String userName;

    @JsonProperty(value = "email")
    private final String email;

    @JsonProperty(value = "password")
    private final String password;

    public UserRegisterModel toModel() {
        return new UserRegisterModel(userName, email, password);
    }
}
