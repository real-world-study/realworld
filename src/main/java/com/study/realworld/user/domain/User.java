package com.study.realworld.user.domain;

import com.study.realworld.global.domain.BaseTimeEntity;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
@Where(clause = "deleted_at is null")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Profile profile;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private FollowingUsers followingUsers = new FollowingUsers();

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

    public Bio bio() {
        return profile.bio();
    }

    public Image image() {
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

    public Profile profile() {
        return profile;
    }

    public Profile profileByFollowee(User followee) {
        return followee.profile.profileByFollowing(isFollow(followee));
    }

    private boolean isFollow(User user) {
        return followingUsers.isFollow(user);
    }

    public void followingUser(User user) {
        followingUsers.followingUser(user);
    }

    public void unfollowingUser(User user) {
        followingUsers.unfollowingUser(user);
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
