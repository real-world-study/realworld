package com.study.realworld.user.service.model;

import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Image;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
import java.util.Optional;

public class UserUpdateModel {

    private final Username username;
    private final Email email;
    private final Password password;
    private final Bio bio;
    private final Image image;

    public UserUpdateModel(Username username, Email email, Password password, Bio bio, Image image) {
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

    public Optional<Bio> getBio() {
        return Optional.ofNullable(bio);
    }

    public Optional<Image> getImage() {
        return Optional.ofNullable(image);
    }

}
