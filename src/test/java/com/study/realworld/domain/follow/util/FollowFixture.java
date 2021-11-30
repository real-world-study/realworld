package com.study.realworld.domain.follow.util;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.user.domain.persist.User;

public class FollowFixture {
    public static Follow createFollow(final User followee, final User follower) {
        return Follow.builder()
                .followee(followee)
                .follower(follower)
                .build();
    }
}
