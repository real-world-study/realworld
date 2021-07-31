package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("User 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final User user = new User();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class)
        );
    }

    @DisplayName("User 인스턴스 빌더 테스트")
    @Test
    void builder_test() {
        final User user = User.Builder()
                .email("email")
                .username("username")
                .password("password")
                .bio("bio")
                .image("image")
                .build();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class)
        );
    }
}