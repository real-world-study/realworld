package com.study.realworld.user.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author Jeongjoon Seo
 */
@JsonRootName(value = "user")
@Builder
@AllArgsConstructor
public class UserResponse {

    @JsonProperty(value = "username")
    @NotNull
    private final String userName;
    @NotNull
    @JsonProperty(value = "email")
    private final String email;
    @JsonProperty(value = "bio")
    private final String bio;
    @JsonProperty(value = "image")
    private final String image;
}
