package com.study.realworld.user.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Profile {

    @Embedded
    private Username username;

    @Embedded
    private Bio bio;

    @Embedded
    private Image image;

    protected Profile() {
    }

    private Profile(Username username, Bio bio, Image image) {
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public Username username() {
        return username;
    }

    public Bio bio() {
        return bio;
    }

    public Image image() {
        return image;
    }

    public void changeUsername(Username username) {
        this.username = username;
    }

    public void changeBio(Bio bio) {
        this.bio = bio;
    }

    public void changeImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        return Objects.equals(username, profile.username) && Objects.equals(bio, profile.bio)
            && Objects.equals(image, profile.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, bio, image);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private Username username;
        private Bio bio;
        private Image image;

        private Builder() {
        }

        public Builder username(Username username) {
            this.username = username;
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

        public Profile build() {
            return new Profile(username, bio, image);
        }
    }

}
