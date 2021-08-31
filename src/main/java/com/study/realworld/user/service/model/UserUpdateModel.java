package com.study.realworld.user.service.model;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.Username;
import java.util.Optional;

public class UserUpdateModel {

    private final Username username;
    private final Email email;
    private final Password password;
    private final String bio;
    private final Image image;

    public UserUpdateModel(Username username, Email email, Password password, String bio, Image image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public Optional<Username> getUsername() {
        return Optional.ofNullable(username);
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<Password> getPassword() {
        return Optional.ofNullable(password);
    }

    public Optional<String> getBio() {
        return Optional.ofNullable(bio);
    }

    public Optional<Image> getImage() {
        return Optional.ofNullable(image);
    }

}
