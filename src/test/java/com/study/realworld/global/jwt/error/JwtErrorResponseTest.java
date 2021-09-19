package com.study.realworld.global.jwt.error;

import com.study.realworld.global.jwt.error.exception.JwtAuthenticationException;
import com.study.realworld.global.jwt.error.exception.JwtErrorCodeNullPointerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("JwtErrorResponse 인스턴스 기본 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtErrorCode jwtErrorCode = JwtErrorCode.values(new JwtAuthenticationException("test"));
        final JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(jwtErrorCode);

        assertAll(
                () -> assertThat(jwtErrorResponse).isNotNull(),
                () -> assertThat(jwtErrorResponse).isExactlyInstanceOf(JwtErrorResponse.class)
        );
    }

    @DisplayName("JwtErrorResponse 인스턴스 null 주입시 예외처리 테스트")
    @Test
    void constructor_null_test() {

        assertThatThrownBy(() -> new JwtErrorResponse(null))
                .isInstanceOf(JwtErrorCodeNullPointerException.class)
                .hasMessage("JwtErrorCode is null");
    }

}