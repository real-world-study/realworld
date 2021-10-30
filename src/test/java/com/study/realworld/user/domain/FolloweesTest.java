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

    @Nested
    @DisplayName("특정 User를 follow할 수 있다.")
    class followingUserTest {

        @Test
        @DisplayName("정상적인 경우 user가 포함된다.")
        void successTest() {

            // given
            Set<User> userSet = new HashSet<>();
            Followees followees = Followees.of(userSet);

            Set<User> expectedUserSet = new HashSet<>();
            expectedUserSet.add(followee);
            Followees expected = Followees.of(expectedUserSet);

            // when
            Followees result = followees.followingUser(followee);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("이미 follow한 유저를 follow할 경우 exception이 발생해야 한다.")
        void exceptionTest() {

            // given
            Set<User> userSet = new HashSet<>();
            userSet.add(followee);
            Followees followees = Followees.of(userSet);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followees.followingUser(followee))
                .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
        }

    }

    @Nested
    @DisplayName("특정 User를 unfollow할 수 있다.")
    class unfollowingUserTest {

        @Test
        @DisplayName("정상적인 경우 user가 제거된다.")
        void successTest() {

            // given
            Set<User> userSet = new HashSet<>();
            userSet.add(followee);
            Followees followees = Followees.of(userSet);

            Set<User> expectedUserSet = new HashSet<>();
            Followees expected = Followees.of(expectedUserSet);

            // when
            Followees result = followees.unfollowingUser(followee);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("이미 unfollow한 유저를 unfollow할 경우 exception이 발생해야 한다.")
        void exceptionTest() {

            // given
            Set<User> userSet = new HashSet<>();
            Followees followees = Followees.of(userSet);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followees.unfollowingUser(followee))
                .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
        }

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