package com.study.realworld.user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.Username;

public class ProfileResponse {

    @JsonProperty("profile")
    private ProfileResponseNested profileResponseNested;

    ProfileResponse() {
    }

    private ProfileResponse(ProfileResponseNested profileResponseNested) {
        this.profileResponseNested = profileResponseNested;
    }

    public static ProfileResponse ofProfile(Profile profile) {
        return new ProfileResponse(ProfileResponseNested.ofProfile(profile));
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

        public static ProfileResponseNested ofProfile(Profile profile) {
            return new ProfileResponseNested (
                profile.username(),
                profile.bio(),
                profile.image(),
                profile.isFollow()
            );
        }

    }

}
