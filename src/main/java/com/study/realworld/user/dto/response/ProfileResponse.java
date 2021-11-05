package com.study.realworld.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.util.Objects;

public class ProfileResponse {

    @JsonProperty("profile")
    private ProfileResponseNested profileResponseNested;

    ProfileResponse() {
    }

    private ProfileResponse(ProfileResponseNested profileResponseNested) {
        this.profileResponseNested = profileResponseNested;
    }

    public static ProfileResponse fromUserAndFollowing(User user, boolean following) {
        return new ProfileResponse(
            ProfileResponseNested.fromProfileAndFollowing(user.profile(), following)
        );
    }

    public static ProfileResponse fromProfileAndFollowing(Profile profile, boolean following) {
        return new ProfileResponse(
            ProfileResponseNested.fromProfileAndFollowing(profile, following)
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
        return Objects.equals(profileResponseNested, that.profileResponseNested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileResponseNested);
    }

    public static class ProfileResponseNested {

        @JsonProperty("username")
        private Username username;

        @JsonProperty("bio")
        private Bio bio;

        @JsonProperty("image")
        private Image image;

        @JsonProperty("following")
        private boolean following;

        ProfileResponseNested() {
        }

        private ProfileResponseNested(Username username, Bio bio, Image image, boolean following) {
            this.username = username;
            this.bio = bio;
            this.image = image;
            this.following = following;
        }

        public static ProfileResponseNested fromProfileAndFollowing(Profile profile, boolean following) {
            return new ProfileResponseNested(
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
            ProfileResponseNested that = (ProfileResponseNested) o;
            return following == that.following && Objects.equals(username, that.username) && Objects
                .equals(bio, that.bio) && Objects.equals(image, that.image);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, bio, image, following);
        }

    }

}
