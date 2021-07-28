package com.study.realworld.user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;

import static java.lang.String.valueOf;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserResponse {
    @JsonProperty("username")
    private final String username;
    @JsonProperty("email")
    private final String email;
    @JsonProperty("bio")
    private final String bio;
    @JsonProperty("image")
    private final String image;

    protected UserResponse(String username, String email, String bio, String image) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                valueOf(user.getUsername()),
                valueOf(user.getEmail()),
                valueOf(user.getBio()),
                valueOf(user.getImage())
        );
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

}
