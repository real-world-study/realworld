package com.study.realworld.global.jwt.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtErrorResponseTest {

    @DisplayName("JwtErrorResponse 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final JwtErrorResponse jwtErrorResponse = new JwtErrorResponse();

        assertAll(
                () -> assertThat(jwtErrorResponse).isNotNull(),
                () -> assertThat(jwtErrorResponse).isExactlyInstanceOf(JwtErrorResponse.class)
        );
    }

}