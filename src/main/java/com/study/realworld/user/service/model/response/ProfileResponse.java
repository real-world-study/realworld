package com.study.realworld.user.service.model.response;

import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.Username;
import java.util.Objects;

public class ProfileResponse {

    private Username username;

    private Bio bio;

    private Image image;

    private boolean following;

    private ProfileResponse(Username username, Bio bio, Image image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    public static ProfileResponse from(Profile profile, boolean following) {
        return new ProfileResponse(
            profile.username(),
            profile.bio(),
            profile.image(),
            following
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileResponse that = (ProfileResponse) o;
        return following == that.following && Objects.equals(username, that.username) && Objects
            .equals(bio, that.bio) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, bio, image, following);
    }

}
