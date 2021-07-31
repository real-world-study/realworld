package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public final class UserJoinRequest {

    private final String username;
    private final String email;
    private final String password;

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

}
