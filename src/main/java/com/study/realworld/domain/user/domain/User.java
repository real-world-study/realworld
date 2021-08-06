package com.study.realworld.domain.user.domain;

import com.study.realworld.domain.BaseTimeEntity;

import javax.persistence.*;

@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "user_username", length = 20, nullable = false)
    private String username;

    @Column(name = "uesr_password", nullable = false)
    private String password;

    @Column(name = "user_bio")
    private String bio;

    @Column(name = "user_image")
    private String image;

    protected User() {
    }

    private User(final UserBuilder userBuilder) {
        this.email = userBuilder.email;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.bio = userBuilder.bio;
        this.image = userBuilder.image;
    }

    public String email() {
        return email;
    }

    public String username() {
        return username;
    }

    public String bio() {
        return bio;
    }

    public String image() {
        return image;
    }

    public static UserBuilder Builder() {
        return new UserBuilder();
    }

    public boolean checkPassword(final String otherPassword) {
        return password.equals(otherPassword);
    }

    public static class UserBuilder {
        private String email;
        private String username;
        private String password;
        private String bio;
        private String image;

        private UserBuilder() {
        }

        public UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserBuilder bio(final String bio) {
            this.bio = bio;
            return this;
        }

        public UserBuilder image(final String image) {
            this.image = image;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}