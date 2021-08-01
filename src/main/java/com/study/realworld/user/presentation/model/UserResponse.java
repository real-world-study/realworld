package com.study.realworld.user.presentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.core.domain.user.entity.User;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * @author Jeongjoon Seo
 */
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
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

    public static UserResponse createResponse(User user) {
        return UserResponse.builder()
                           .userName(user.getUserName())
                           .email(user.getEmail())
                           .bio(user.getBio())
                           .image(user.getImage())
                           .build();
    }
}
