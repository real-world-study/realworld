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
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD_ENCODER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserJoinRequestTest {

    @DisplayName("UserJoinRequest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(PASSWORD));

        assertAll(
                () -> assertThat(userJoinRequest).isNotNull(),
                () -> assertThat(userJoinRequest).isExactlyInstanceOf(UserJoinRequest.class)
        );
    }

    @DisplayName("UserJoinRequest 인스턴스 toEntity() 테스트")
    @Test
    void toEntity_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(PASSWORD));
        final User user = userJoinRequest.toUser().encode(PASSWORD_ENCODER); // 동일 객체는 디미터 법칙 위배가 아니다.

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class),
                () -> assertThat(user.username().name()).isEqualTo(USERNAME),
                () -> assertThat(user.email().email()).isEqualTo(EMAIL),
                () -> assertThat(user.matches(PASSWORD, PASSWORD_ENCODER)).isTrue()
        );
    }

    public static final UserJoinRequest userJoinRequest(final Name username, final Email email, final Password password) {
        return new UserJoinRequest(username, email, password);
    }

}