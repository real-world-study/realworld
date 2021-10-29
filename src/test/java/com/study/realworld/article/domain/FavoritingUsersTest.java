package com.study.realworld.article.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FavoritingUsersTest {

    private User user;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
    }

    @Test
    void favoritingUsersTest() {
        FavoritingUsers favoritingUsers = new FavoritingUsers();
    }

    @Nested
    @DisplayName("favorited 여부를 확인할 수 있다.")
    class isFavoritedTest {

        @Test
        @DisplayName("favorited된 상태일 때 true여야 한다.")
        void trueTest() {

            // given
            Set<User> userSet = new HashSet<>();
            userSet.add(user);
            FavoritingUsers favoritingUsers = FavoritingUsers.of(userSet);

            // when
            boolean result = favoritingUsers.isFavorite(user);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("favorited 안된 상태일 때 false여야 한다.")
        void falseTest() {

            // given
            Set<User> userSet = new HashSet<>();
            FavoritingUsers favoritingUsers = FavoritingUsers.of(userSet);

            // when
            boolean result = favoritingUsers.isFavorite(user);

            // then
            assertFalse(result);
        }

    }

}