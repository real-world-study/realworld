package com.study.realworld.jwt;

import com.study.realworld.user.domain.User;

public class AuthenticationResponse {

    private final String accessToken;

    private final User user;

    public AuthenticationResponse(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getUser() {
        return user;
    }

}
