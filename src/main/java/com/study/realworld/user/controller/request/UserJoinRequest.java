package com.study.realworld.user.controller.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserJoinRequest {

    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;

    public User toEntity() {
        return User.builder()
            .username(this.username)
            .email(this.email)
            .password(this.password)
            .bio(this.bio)
            .image(this.image)
            .build();
    }

}
