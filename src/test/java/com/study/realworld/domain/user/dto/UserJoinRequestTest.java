package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Name;
import com.study.realworld.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.UserTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserJoinRequestTest {

    @DisplayName("UserJoinRequest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest(new Name(USERNAME), new Email(EMAIL), PASSWORD);

        assertAll(
                () -> assertThat(userJoinRequest).isNotNull(),
                () -> assertThat(userJoinRequest).isExactlyInstanceOf(UserJoinRequest.class)
        );
    }

    @DisplayName("UserJoinRequest 인스턴스 toEntity() 테스트")
    @Test
    void toEntity_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest(new Name(USERNAME), new Email(EMAIL), PASSWORD);
        final User user = userJoinRequest.toUser();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class),
                () -> assertThat(user.username().name()).isEqualTo(USERNAME),
                () -> assertThat(user.email().email()).isEqualTo(EMAIL),
                () -> assertThat(user.checkPassword(PASSWORD)).isTrue()
        );
    }

    public static final UserJoinRequest userJoinRequest(final Name username, final Email email, final String password) {
        return new UserJoinRequest(username, email, password);
    }

}