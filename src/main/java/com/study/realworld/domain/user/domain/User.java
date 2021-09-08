package com.study.realworld.domain.user.domain;

import com.study.realworld.domain.BaseTimeEntity;
import com.study.realworld.domain.user.exception.PasswordMissMatchException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static java.util.Objects.isNull;

@Entity
public class User extends BaseTimeEntity {

    public static final String DEFAULT_AUTHORITY = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "email", column =
    @Column(name = "email", length = 50, nullable = false, unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "name", column =
    @Column(name = "username", length = 20, nullable = false))
    private Name username;

    @Embedded
    @AttributeOverride(name = "password", column =
    @Column(name = "password", nullable = false))
    private Password password;

    @Embedded
    @AttributeOverride(name = "bio", column =
    @Column(name = "bio"))
    private Bio bio;

    @Embedded
    @AttributeOverride(name = "path", column =
    @Column(name = "image"))
    private Image image;

    protected User() {
    }

    protected User(final UserBuilder userBuilder) {
        this.email = userBuilder.email;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.bio = userBuilder.bio;
        this.image = userBuilder.image;
    }

    public void login(final Password rawPassword, final PasswordEncoder passwordEncoder) {
        if(!password.matches(rawPassword, passwordEncoder)){
            throw new PasswordMissMatchException();
        }
    }

    public User encode(final PasswordEncoder passwordEncoder) {
        this.password = Password.encode(password, passwordEncoder);
        return this;
    }

    public Long id() {
        return id;
    }

    public Email email() {
        return email;
    }

    public Name username() {
        return username;
    }

    public Bio bio() {
        return bio;
    }

    public Image image() {
        return image;
    }

    public Password password() {
        return password;
    }

    public User changeEmail(final Email email) {
        validateArgumentNull(email);
        this.email = email;
        return this;
    }

    public User changeBio(final Bio bio) {
        validateArgumentNull(bio);
        this.bio = bio;
        return this;
    }

    public User changeImage(final Image image) {
        validateArgumentNull(image);
        this.image = image;
        return this;
    }

    private void validateArgumentNull(final Object argument) {
        if(isNull(argument)) {
            throw new IllegalArgumentException();
        }
    }

    public static UserBuilder Builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Email email;
        private Name username;
        private Password password;
        private Bio bio;
        private Image image;
      
        private UserBuilder() {
        }

        public UserBuilder email(final Email email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(final Name username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(final Password password) {
            this.password = password;
            return this;
        }

        public UserBuilder bio(final Bio bio) {
            this.bio = bio;
            return this;
        }

        public UserBuilder image(final Image image) {
            this.image = image;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}