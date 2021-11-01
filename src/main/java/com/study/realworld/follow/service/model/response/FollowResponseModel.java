package com.study.realworld.follow.service.model.response;

import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.service.model.response.ProfileResponseModel;
import java.util.Objects;

public class FollowResponseModel {

    private ProfileResponseModel profileResponseModel;

    private FollowResponseModel(ProfileResponseModel profileResponseModel) {
        this.profileResponseModel = profileResponseModel;
    }

    public static FollowResponseModel from(Profile profile, boolean following) {
        return new FollowResponseModel(
            ProfileResponseModel.from(profile, following)
        );
    }

    public ProfileResponseModel profileResponseModel() {
        return profileResponseModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FollowResponseModel that = (FollowResponseModel) o;
        return Objects.equals(profileResponseModel, that.profileResponseModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileResponseModel);
    }

}
