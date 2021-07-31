package com.study.realworld.domain.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.UserTest.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserJoinResponseTest {

    public static final UserJoinResponse USER_JOIN_RESPONSE = UserJoinResponse.fromUser(USER);

    @DisplayName("UserJoinResponse 인스턴스 fromUser() 테스트")
    @Test
    void fromUser_test() {
        final UserJoinResponse userJoinResponse = UserJoinResponse.fromUser(USER);

        assertAll(
                () -> assertThat(userJoinResponse).isNotNull(),
                () -> assertThat(userJoinResponse).isExactlyInstanceOf(UserJoinResponse.class)
        );
    }


}