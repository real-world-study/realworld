package com.tistory.povia.realworld.user.domain;

import com.tistory.povia.realworld.common.domain.BaseTimeEntity;
import com.tistory.povia.realworld.common.exception.EmailException;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class User extends BaseTimeEntity {

    @Column(name = "id")
    private Long id;

    @Embedded private Email email;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    private User(Long id, Email email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public Long id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public void changeEmail(String address) {
        email.changeEmail(address);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    private void checkEmail(String email) {
        if (email.isBlank()) {
            throw new EmailException();
        }
    }

    static class Builder {
        private Long id;
        private Email email;
        private String username;
        private String password;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(id, email, username, password);
        }
    }
}
