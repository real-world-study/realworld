package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserJoinRequestTest {

    @DisplayName("UserJoinRequest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest("username", "email", "password");

        assertAll(
                () -> assertThat(userJoinRequest).isNotNull(),
                () -> assertThat(userJoinRequest).isExactlyInstanceOf(UserJoinRequest.class)
        );
    }

    @DisplayName("UserJoinRequest 인스턴스 toEntity() 테스트")
    @Test
    void toEntity_test() {
        final String username = "username";
        final String email = "email";
        final String password = "password";
        final UserJoinRequest userJoinRequest = new UserJoinRequest(username, email, password);
        final User user =  userJoinRequest.toEntity();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class),
                () -> assertThat(user.username()).isEqualTo(username),
                () -> assertThat(user.email()).isEqualTo(email),
                () -> assertThat(user.checkPassword(password))
        );
    }


}