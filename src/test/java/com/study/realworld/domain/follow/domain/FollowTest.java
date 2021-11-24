package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("팔로우 (Follow)")
class FollowTest {

    @Test
    void 두명의_유저들을_토대로_객체를_생성할_수_있다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User other = testUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final Follow follow = testFollower(other, user);

        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow).isExactlyInstanceOf(Follow.class)
        );
    }

    @Test
    void 팔로위_팔로워_정보를_반환한다() {
        final User follower = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User followee = testUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final Follow follow = testFollower(followee, follower);
        ReflectionTestUtils.setField(follow, "id", 1L);

        assertAll(
                () -> assertThat(follow.id()).isEqualTo(1L),
                () -> assertThat(follow.followee()).isEqualTo(followee),
                () -> assertThat(follow.follower()).isEqualTo(follower)
        );
    }

    public static Follow testFollower(final User other, final User user) {
        return Follow.builder()
                .followee(other)
                .follower(user)
                .build();
    }
}