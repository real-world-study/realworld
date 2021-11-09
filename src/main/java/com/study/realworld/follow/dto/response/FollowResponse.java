package com.study.realworld.follow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.user.domain.vo.Profile;
import com.study.realworld.user.dto.response.ProfileResponse.ProfileResponseNested;
import java.util.Objects;

public class FollowResponse {

    @JsonProperty("profile")
    private ProfileResponseNested profileResponseNested;

    FollowResponse() {
    }

    private FollowResponse(ProfileResponseNested profileResponseNested) {
        this.profileResponseNested = profileResponseNested;
    }

    public static FollowResponse fromProfileAndFollowing(Profile profile, boolean following) {
        return new FollowResponse(ProfileResponseNested.fromProfileAndFollowing(profile, following));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FollowResponse that = (FollowResponse) o;
        return Objects.equals(profileResponseNested, that.profileResponseNested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileResponseNested);
    }

}
