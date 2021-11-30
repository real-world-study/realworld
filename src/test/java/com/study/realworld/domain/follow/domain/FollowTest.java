package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.follow.util.FollowFixture.createFollow;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("팔로우 (Follow)")
public class FollowTest {

    @Test
    void 팔로우는_식별자를_기준으로_동등성_비교를한다() {
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final Follow follow = createFollow(followee, follower);
        final Follow other = createFollow(followee, follower);

        ReflectionTestUtils.setField(follow, "followId", 1L);
        ReflectionTestUtils.setField(other, "followId", 1L);

        assertAll(
                () -> assertThat(follow).isEqualTo(other),
                () -> assertThat(follow).hasSameHashCodeAs(other)
        );
    }

    @Test
    void 팔로위_팔로워_정보를_반환한다() {
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final Follow follow = createFollow(followee, follower);

        ReflectionTestUtils.setField(follow, "followId", 1L);

        assertAll(
                () -> assertThat(follow.followId()).isEqualTo(1L),
                () -> assertThat(follow.followee()).isEqualTo(followee),
                () -> assertThat(follow.follower()).isEqualTo(follower)
        );
    }
}
