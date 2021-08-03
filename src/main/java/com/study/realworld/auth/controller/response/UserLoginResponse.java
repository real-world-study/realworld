package com.study.realworld.auth.controller.response;

import static java.lang.String.valueOf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserLoginResponse {

    @JsonProperty("username")
    private final String username;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("bio")
    private final String bio;

    @JsonProperty("image")
    private final String image;

    @JsonProperty("token")
    private final String token;

    protected UserLoginResponse(String username, String email, String bio, String image,
        String token) {
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

    public static UserLoginResponse fromUserAndToken(User user, String accessToken) {
        return new UserLoginResponse(
            valueOf(user.getUsername()),
            valueOf(user.getEmail()),
            valueOf(user.getBio()),
            valueOf(user.getImage()),
            valueOf(accessToken)
        );
    }

}
