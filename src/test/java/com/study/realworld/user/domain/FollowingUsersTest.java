package com.study.realworld.user.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FollowingUsersTest {

    private User user;
    private User followingUser;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        followingUser = User.Builder()
            .profile(Username.of("followingUser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();
    }

    @Nested
    @DisplayName("follow 여부를 확인할 수 있다.")
    class isFollowTest {

        @Test
        @DisplayName("follow된 상태일 때 true여야 한다.")
        void trueTest() {

            // given
            Set<User> userSet = new HashSet<>();
            userSet.add(followingUser);
            FollowingUsers followingUsers = FollowingUsers.of(userSet);

            // when
            boolean result = followingUsers.isFollow(followingUser);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("follow안된 상태일 때 false여야 한다.")
        void falseTest() {

            // given
            Set<User> userSet = new HashSet<>();
            FollowingUsers followingUsers = FollowingUsers.of(userSet);

            // when
            boolean result = followingUsers.isFollow(followingUser);

            // then
            assertFalse(result);
        }
    }

}