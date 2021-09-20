package com.study.realworld.user.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
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
    private Profile profile;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @JoinTable(name = "follow",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> followingUsers = new HashSet<>();

    protected User() {
    }

    private User(Long id, Profile profile, Email email, Password password) {
        this.id = id;
        this.profile = profile;
        this.email = email;
        this.password = password;
    }

    public Long id() {
        return id;
    }

    public Username username() {
        return profile.username();
    }

    public Email email() {
        return email;
    }

    public Password password() {
        return password;
    }

    public Optional<Bio> bio() {
        return profile.bio();
    }

    public Optional<Image> image() {
        return profile.image();
    }

    public void changeUsername(Username username) {
        profile.changeUsername(username);
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePassword(Password password, PasswordEncoder passwordEncoder) {
        this.password = Password.encode(password, passwordEncoder);
    }

    public void changeBio(Bio bio) {
        profile.changeBio(bio);
    }

    public void changeImage(Image image) {
        profile.changeImage(image);
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

    public static class Builder {

        private Long id;
        private Profile profile;
        private Email email;
        private Password password;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder profile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public Builder profile(Username username, Bio bio, Image image) {
            this.profile = Profile.Builder()
                .username(username)
                .bio(bio)
                .image(image)
                .build();
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

        public User build() {
            return new User(id, profile, email, password);
        }
    }

}
