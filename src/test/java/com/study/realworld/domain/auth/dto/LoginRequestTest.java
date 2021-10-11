package com.study.realworld.domain.auth.dto;

import com.study.realworld.domain.user.domain.vo.Email;
import com.study.realworld.domain.user.domain.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginRequestTest {

    @DisplayName("LoginRequest 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final LoginRequest loginRequest = new LoginRequest();

        assertAll(
                () -> assertThat(loginRequest).isNotNull(),
                () -> assertThat(loginRequest).isExactlyInstanceOf(LoginRequest.class)
        );
    }

    @DisplayName("LoginRequest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final Email email = new Email(EMAIL);
        final Password password = new Password(PASSWORD);
        final LoginRequest loginRequest = new LoginRequest(email, password);

        assertAll(
                () -> assertThat(loginRequest).isNotNull(),
                () -> assertThat(loginRequest).isExactlyInstanceOf(LoginRequest.class)
        );
    }

    @DisplayName("LoginRequest 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final Email email = new Email(EMAIL);
        final Password password = new Password(PASSWORD);
        final LoginRequest loginRequest = new LoginRequest(email, password);

        assertAll(
                () -> assertThat(loginRequest.email()).isEqualTo(email),
                () -> assertThat(loginRequest.password()).isEqualTo(password)
        );
    }

    public static final LoginRequest loginRequest(final Email email, final Password password) {
        return new LoginRequest(email, password);
    }

}