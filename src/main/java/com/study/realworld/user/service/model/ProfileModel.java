package com.study.realworld.user.service.model;

import com.study.realworld.user.domain.Profile;

public class ProfileModel {

    private Profile profile;

    private boolean follow;

    private ProfileModel(Profile profile, boolean follow) {
        this.profile = profile;
        this.follow = follow;
    }

    public static ProfileModel fromProfileAndFollowing(Profile profile, boolean follow) {
        return new ProfileModel(profile, follow);
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean isFollow() {
        return follow;
    }

}
