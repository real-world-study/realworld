package com.study.realworld.user.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    private Bio bio;

    @Column(name = "image")
    private Image image;

    @JoinTable(name = "follow",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> followingUsers = new HashSet<>();

    protected User() {
    }

    private User(Long id, Username username, Email email, Password password, Bio bio, Image image) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public Long id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public Email email() {
        return email;
    }

    public Password password() {
        return password;
    }

    public Optional<Bio> bio() {
        return Optional.ofNullable(bio);
    }

    public Optional<Image> image() {
        return Optional.ofNullable(image);
    }

    public void changeUsername(Username username) {
        this.username = username;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePassword(Password password, PasswordEncoder passwordEncoder) {
        this.password = Password.encode(password, passwordEncoder);
    }

    public void changeBio(Bio bio) {
        this.bio = bio;
    }

    public void changeImage(Image image) {
        this.image = image;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = Password.encode(this.password, passwordEncoder);
    }

    public void login(Password rawPassword, PasswordEncoder passwordEncoder) {
        this.password.matchPassword(rawPassword, passwordEncoder);
    }

    public void followingUser(User user) {
        checkFollowingUser(user);
        followingUsers.add(user);
    }

    private void checkFollowingUser(User user) {
        if (followingUsers.contains(user)) {
            throw new RuntimeException("follow user exception");
        }
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
        private Bio bio;
        private Image image;

        private Builder() {
        }

        private Builder(User user) {
            id = user.id;
            username = user.username;
            email = user.email;
            password = user.password;
            bio = user.bio;
            image = user.image;
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

        public Builder bio(Bio bio) {
            this.bio = bio;
            return this;
        }

        public Builder image(Image image) {
            this.image = image;
            return this;
        }

        public User build() {
            return new User(id, username, email, password, bio, image);
        }
    }

}
