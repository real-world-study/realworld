package com.study.realworld.user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.Username;

@JsonTypeName("profile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ProfileResponse {

    @JsonProperty("username")
    private Username username;

    @JsonProperty("bio")
    private Bio bio;

    @JsonProperty("image")
    private Image image;

    @JsonProperty("following")
    private boolean following;

    protected ProfileResponse() {
    }

    private ProfileResponse(Username username, Bio bio, Image image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    public Username getUsername() {
        return username;
    }

    public Bio getBio() {
        return bio;
    }

    public Image getImage() {
        return image;
    }

    public boolean isFollowing() {
        return following;
    }

    public static ProfileResponse ofProfile(Profile profile) {
        return new ProfileResponse(
            profile.username(),
            profile.bio(),
            profile.image(),
            profile.isFollow()
        );
    }

}
