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
    private final String email;
    private final String bio;
    private final String image;
}
