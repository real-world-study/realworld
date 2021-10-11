package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.vo.Email;
import com.study.realworld.domain.user.domain.vo.Name;
import com.study.realworld.domain.user.domain.vo.Password;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD_ENCODER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserJoinRequestTest {

    @DisplayName("UserJoinRequest 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final UserJoinRequest userJoinRequest = new UserJoinRequest();

        assertAll(
                () -> assertThat(userJoinRequest).isNotNull(),
                () -> assertThat(userJoinRequest).isExactlyInstanceOf(UserJoinRequest.class)
        );
    }

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
                () -> assertThat(user.email().email()).isEqualTo(EMAIL)
        );
    }

    public static final UserJoinRequest userJoinRequest(final Name username, final Email email, final Password password) {
        return new UserJoinRequest(username, email, password);
    }

}