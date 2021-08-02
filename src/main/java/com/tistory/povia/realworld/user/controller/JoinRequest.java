package com.tistory.povia.realworld.user.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import java.util.Objects;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class JoinRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String address;

    @JsonProperty("password")
    private String password;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("image")
    private String image;

    JoinRequest() {}

    JoinRequest(String username, String address, String password, String bio, String image) {
        this.username = username;
        this.address = address;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public String username() {
        return username;
    }

    public String address() {
        return address;
    }

    public String password() {
        return password;
    }

    public String bio() {
        return bio;
    }

    public String image() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinRequest that = (JoinRequest) o;
        return Objects.equals(username, that.username)
                && Objects.equals(address, that.address)
                && Objects.equals(password, that.password)
                && Objects.equals(bio, that.bio)
                && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, address, password, bio, image);
    }

    public User toUser() {
        return User.builder()
                .email(new Email(address))
                .username(username)
                .password(password)
                .image(image)
                .bio(bio)
                .build();
    }
}
