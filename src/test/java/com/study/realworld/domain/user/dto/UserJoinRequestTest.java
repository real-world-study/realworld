package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserJoinRequestTest {

    public static final UserJoinRequest USER_JOIN_REQUEST = new UserJoinRequest(EMAIL, USERNAME, PASSWORD);

    @DisplayName("UserJoinRequest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest(EMAIL, USERNAME, PASSWORD);

        assertAll(
                () -> assertThat(userJoinRequest).isNotNull(),
                () -> assertThat(userJoinRequest).isExactlyInstanceOf(UserJoinRequest.class)
        );
    }

    @DisplayName("UserJoinRequest 인스턴스 toEntity() 테스트")
    @Test
    void toEntity_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest(EMAIL, USERNAME, PASSWORD);
        final User user =  userJoinRequest.toEntity();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class),
                () -> assertThat(user.email()).isEqualTo(EMAIL),
                () -> assertThat(user.username()).isEqualTo(USERNAME),
                () -> assertThat(user.checkPassword(PASSWORD)).isTrue()
        );
    }


}