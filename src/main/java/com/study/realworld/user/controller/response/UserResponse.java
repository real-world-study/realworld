package com.study.realworld.user.controller.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserResponse {

    private String username;
    private String email;
    private String bio;
    private String image;
    private String token;

    public static UserResponse fromUserAndToken(User user, String accessToken) {
        return new UserResponse(
            user.getUsername(),
            user.getEmail(),
            user.getBio(),
            user.getImage(),
            accessToken
        );
    }

}
