package com.study.realworld.follow.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.follow.service.model.response.FollowResponseModel;
import com.study.realworld.user.controller.response.ProfileResponse.ProfileResponseNested;

public class FollowResponse {

    @JsonProperty("profile")
    private ProfileResponseNested profileResponseNested;

    FollowResponse() {
    }

    private FollowResponse(ProfileResponseNested profileResponseNested) {
        this.profileResponseNested = profileResponseNested;
    }

    public static FollowResponse of(FollowResponseModel model) {
        return new FollowResponse(
            ProfileResponseNested.ofProfileResponseModel(model.profileResponseModel())
        );
    }

}
