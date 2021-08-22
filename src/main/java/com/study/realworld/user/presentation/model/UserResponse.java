package com.study.realworld.user.presentation.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.core.domain.user.entity.User;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * @author Jeongjoon Seo
 */
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Builder
@AllArgsConstructor
@Getter
public class UserResponse {

    @NotNull
    private final String username;

    @NotNull
    private final String email;

    private final String bio;

    private final String image;

    private final String accessToken;

    public static UserResponse createResponse(User user, String accessToken) {
        return UserResponse.builder()
                           .username(user.getUsername())
                           .email(user.getEmail())
                           .bio(user.getBio())
                           .image(user.getImage())
                           .accessToken(accessToken)
                           .build();
    }
}
