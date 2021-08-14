package com.study.realworld.domain.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @DisplayName("LoginRequest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final LoginRequest loginRequest = new LoginRequest();

        assertAll(
                () -> assertThat(loginRequest).isNotNull(),
                () -> assertThat(loginRequest).isExactlyInstanceOf(LoginRequest.class)
        );
    }

}