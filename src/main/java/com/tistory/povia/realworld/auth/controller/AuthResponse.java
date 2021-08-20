package com.tistory.povia.realworld.auth.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tistory.povia.realworld.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthResponse {

    private String email;

    private String token;

    private String username;

    private String bio;

    private String image;

    public AuthResponse() {}

    public AuthResponse(String email, String token, String username, String bio, String image) {
        this.email = email;
        this.token = token;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public String email() {
        return email;
    }

    public String token() {
        return token;
    }

    public String username() {
        return username;
    }

    public String bio() {
        return bio;
    }

    public String image() {
        return image;
    }

    public static AuthResponse fromUserAndToken(User user, String token) {
        return new AuthResponse(
                user.email().address(), token, user.username(), user.bio(), user.image());
    }
}
