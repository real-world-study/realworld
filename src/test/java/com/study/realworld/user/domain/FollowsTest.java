package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.study.realworld.follow.domain.Follow;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FollowsTest {

    private User user;
    private User followee;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        followee = User.Builder()
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
    }

    @Test
    void followsTest() {
        Follows follows = new Follows();
    }

    @Nested
    @DisplayName("following 유저 팔로우 기능 테스트")
    class followingTest {

        @Test
        @DisplayName("이미 팔로윙한 유저를 팔로윙하는 경우 exception이 발생해야 한다.")
        void followingExceptionByExistFollowTest() {

            // given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followSet.add(follow);
            Follows follows = Follows.of(followSet);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> follows.following(follow))
                .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
        }

        @Test
        @DisplayName("정상적으로 유저를 팔로우할 수 있다.")
        void followingSuccessTest() {

            // given
            Follows follows = Follows.of(new HashSet<>());
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();

            Set<Follow> followSet = new HashSet<>();
            followSet.add(follow);
            Follows expected = Follows.of(followSet);

            // when
            boolean result = follows.following(follow);

            // then
            assertAll(
                () -> assertThat(follows).isEqualTo(expected),
                () -> assertTrue(result)
            );

        }

    }

    @Nested
    @DisplayName("unfollowing 유저 언팔로우 기능 테스트")
    class unfollowingTest {

        @Test
        @DisplayName("팔로윙 안한 유저를 언팔로윙하는 경우 exception이 발생해야 한다.")
        void followingExceptionByNoExistFollowTest() {

            // given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            Follows follows = Follows.of(followSet);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> follows.unfollowing(follow))
                .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
        }

        @Test
        @DisplayName("정상적으로 유저를 언팔로우할 수 있다.")
        void followingSuccessTest() {

            // given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followSet.add(follow);
            Follows follows = Follows.of(followSet);

            Follows expected = Follows.of(new HashSet<>());

            // when
            boolean result = follows.unfollowing(follow);

            // then
            assertAll(
                () -> assertThat(follows).isEqualTo(expected),
                () -> assertFalse(result)
            );

        }

    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void followsEqualsHashCodeTest() {

        // given
        Set<Follow> followSet = new HashSet<>();
        Follow follow = Follow.builder()
            .follower(user)
            .followee(followee)
            .build();
        followSet.add(follow);

        // when
        Follows result = Follows.of(followSet);

        // then
        assertThat(result)
            .isEqualTo(Follows.of(followSet))
            .hasSameHashCodeAs(Follows.of(followSet));
    }

}
