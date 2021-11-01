package com.study.realworld.follow.service.model.response;

import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.service.model.response.ProfileResponse;
import java.util.Objects;

public class FollowResponse {

    private ProfileResponse profileResponse;

    private FollowResponse(ProfileResponse profileResponse) {
        this.profileResponse = profileResponse;
    }

    public static FollowResponse from(Profile profile, boolean following) {
        return new FollowResponse(
            ProfileResponse.from(profile, following)
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
        FollowResponse that = (FollowResponse) o;
        return Objects.equals(profileResponse, that.profileResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileResponse);
    }

}
