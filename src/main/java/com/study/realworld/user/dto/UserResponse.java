package com.study.realworld.user.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Getter
public class UserResponse {

    private final String email;
    private final String token;
    private final String username;
    private final String bio;
    private final String image;

    @Builder
    public UserResponse(String email, String token, String username, String bio, String image) {
        this.email = email;
        this.token = token;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static UserResponse of(User user, String token) {
        return UserResponse.builder()
                .email(user.getEmail())
                .token(token)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

}
