package com.study.realworld.domain.auth.dto;

import com.study.realworld.domain.user.domain.EmailTest;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.PasswordTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

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
        final LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);

        assertAll(
                () -> assertThat(loginRequest).isNotNull(),
                () -> assertThat(loginRequest).isExactlyInstanceOf(LoginRequest.class)
        );
    }

}