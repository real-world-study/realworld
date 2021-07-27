package com.study.realworld.user.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * @author Jeongjoon Seo
 */
@JsonRootName(value = "user")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRegisterRequest {

    @JsonProperty(value = "username")
    private String userName;
    private String email;
    private String password;

    public UserRegisterModel toModel() {
        return new UserRegisterModel(userName, email, password);
    }
}
