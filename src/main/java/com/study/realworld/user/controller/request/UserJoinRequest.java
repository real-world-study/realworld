package com.study.realworld.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserJoinRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("image")
    private String image;

    protected UserJoinRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    public UserJoinRequest(String username, String email, String password, String bio,
        String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public static User from(UserJoinRequest request) {
        return new User.Builder()
            .username(new Username(request.getUsername()))
            .email(new Email(request.getEmail()))
            .password(new Password(request.getPassword()))
            .bio(request.getBio())
            .image(request.getImage())
            .build();
    }

}
