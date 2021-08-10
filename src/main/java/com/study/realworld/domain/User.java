package com.study.realworld.domain;

public class User {
    private Long id;
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;

    public User(String email, String username, String bio, String image) {
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

}
