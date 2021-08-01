package com.study.realworld.user.controller.dto.response;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import util.TokenGenerator;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfo {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;

    public static UserInfo create(final User user) {
        return builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .token(TokenGenerator.create(user.getEmail()))
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
