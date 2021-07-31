package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.study.realworld.domain.user.domain.User;

@JsonRootName("user")
public final class UserJoinRequest {

    private String username;
    private String email;
    private String password;

    private UserJoinRequest() { }

    UserJoinRequest(final String email, final String username, final String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @JsonProperty("username")
    public final String username() {
        return username;
    }

    @JsonProperty("email")
    public final String email() {
        return email;
    }

    @JsonProperty("password")
    public final String password() {
        return password;
    }

    public final User toEntity() {
        return User.Builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }
}
