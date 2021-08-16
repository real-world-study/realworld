package com.tistory.povia.realworld.auth.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthResponse {

    private String email;

    private String token;

    private String username;

    private String bio;

    private String image;

    public AuthResponse() {}

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
}
