package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Name;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserJoinResponseTest {

    @DisplayName("UserJoinResponse 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final UserJoinResponse userJoinResponse = new UserJoinResponse();

        assertAll(
                () -> assertThat(userJoinResponse).isNotNull(),
                () -> assertThat(userJoinResponse).isExactlyInstanceOf(UserJoinResponse.class)
        );
    }

    @DisplayName("UserJoinResponse 인스턴스 fromUser() 테스트")
    @Test
    void fromUser_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), BIO, IMAGE);
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