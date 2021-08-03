package com.study.realworld.user.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserJoinRequest {

    private String username;
    private String email;
    private String password;

    @Builder
    public UserJoinRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User toUser(UserJoinRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

}

