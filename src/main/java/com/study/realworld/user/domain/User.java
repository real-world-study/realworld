package com.study.realworld.user.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Username username;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    protected User() {
    }

    private User(Long id, Username username, Email email, Password password, String bio,
        String image) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    public void changeUsername(Username username) {
        this.username = username;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void changeBio(String bio) {
        this.bio = bio;
    }

    public void changeImage(String image) {
        this.image = image;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = Password.encode(this.password, passwordEncoder);
    }

    public void login(Password rawPassword, PasswordEncoder passwordEncoder) {
        this.password.matchPassword(rawPassword, passwordEncoder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static Builder Builder(User user) {
        return new Builder(user);
    }

    public static class Builder {

        private Long id;
        private Username username;
        private Email email;
        private Password password;
        private String bio;
        private String image;

        private Builder() {
        }

        private Builder(User user) {
            id = user.getId();
            username = user.getUsername();
            email = user.getEmail();
            password = user.getPassword();
            bio = user.getBio();
            image = user.getImage();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(Username username) {
            this.username = username;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public User build() {
            return new User(id, username, email, password, bio, image);
        }
    }

}
