package com.study.realworld.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String token;
    private String username;
    private String password;
    private String bio;
    private String image;

    @JsonCreator
    public User(@JsonProperty("ID") Long id, @JsonProperty("EMAIL") String email, @JsonProperty("USERNAME") String username,
                @JsonProperty("PASSWORD") String password, @JsonProperty("BIO") String bio, @JsonProperty("IMAGE") String image) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
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

}
