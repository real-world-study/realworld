package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FolloweesTest {

    private User followee;

    @BeforeEach
    void beforeEach() {
        followee = User.Builder()
            .profile(Username.of("followee"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();
    }

    @Test
    void followeesTest() {
        Followees followees = new Followees();
    }

    @Nested
    @DisplayName("follow 여부를 확인할 수 있다.")
    class isFollowTest {

        @Test
        @DisplayName("follow된 상태일 때 true여야 한다.")
        void trueTest() {

            // given
            Set<User> userSet = new HashSet<>();
            userSet.add(followee);
            Followees followees = Followees.of(userSet);

            // when
            boolean result = followees.isFollow(followee);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("follow안된 상태일 때 false여야 한다.")
        void falseTest() {

            // given
            Set<User> userSet = new HashSet<>();
            Followees followees = Followees.of(userSet);

            // when
            boolean result = followees.isFollow(followee);

            // then
            assertFalse(result);
        }
    }

    @Test
    @DisplayName("특정 유저를 follow하고 있을 경우 Exception이 발생해야 한다.")
    void checkIsFollowingUserExceptionTest() {

        // given
        Set<User> userSet = new HashSet<>();
        userSet.add(followee);
        Followees followees = Followees.of(userSet);

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> followees.checkIsFollowingUser(followee))
            .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
    }

    @Test
    @DisplayName("특정 유저를 unfollow하고 있을 경우 Exception이 발생해야 한다.")
    void checkInUnFollowingUserExceptionTest() {

        // given
        Set<User> userSet = new HashSet<>();
        Followees followees = Followees.of(userSet);

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> followees.checkIsFollowingUser(followee))
            .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void followingUserEqualsHashCodeTest() {

        // given
        Set<User> userSet = new HashSet<>();
        userSet.add(followee);
        Followees followees = Followees.of(userSet);

        // when & then
        assertThat(followees)
            .isEqualTo(Followees.of(userSet))
            .hasSameHashCodeAs(Followees.of(userSet));
    }

}