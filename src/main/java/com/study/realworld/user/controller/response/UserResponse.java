package com.study.realworld.user.controller.response;

import static java.lang.String.valueOf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("image")
    private String image;

    @JsonProperty("token")
    private String token;

    protected UserResponse() {
    }

    private UserResponse(String username, String email, String bio, String image, String token) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public static UserResponse fromUserAndToken(User user, String accessToken) {
        return new UserResponse(
            valueOf(user.getUsername()),
            valueOf(user.getEmail()),
            valueOf(user.getBio()),
            valueOf(user.getImage()),
            valueOf(accessToken)
        );
    }

}
