package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserJoinResponseTest {

    @DisplayName("UserJoinResponse 인스턴스 fromUser() 테스트")
    @Test
    void fromUser_test() {
        final User user = UserTest.userBuilder(new Email(EMAIL), USERNAME, PASSWORD, BIO, IMAGE);
        final UserJoinResponse userJoinResponse = UserJoinResponse.fromUser(user);

        assertAll(
                () -> assertThat(userJoinResponse).isNotNull(),
                () -> assertThat(userJoinResponse).isExactlyInstanceOf(UserJoinResponse.class)
        );
    }

    public static final UserJoinResponse userJoinResponse(final User user) {
        return UserJoinResponse.fromUser(user);
    }

}