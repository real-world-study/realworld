package com.study.realworld.global.jwt.error;

import com.study.realworld.global.jwt.error.exception.JwtAuthenticationException;
import com.study.realworld.global.jwt.error.exception.JwtParseException;
import com.study.realworld.global.jwt.error.exception.JwtProviderNotSupportTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class JwtErrorCodeTest {

    @DisplayName("JwtErrorCode 인스턴스 커스텀 values() 테스트")
    @Test
    void values_test() {
        assertAll(
                () -> assertThat(JwtErrorCode.values(new JwtAuthenticationException("test"))).isNotNull(),
                () -> assertThat(JwtErrorCode.values(new JwtProviderNotSupportTypeException("test"))).isNotNull(),
                () -> assertThat(JwtErrorCode.values(new JwtParseException())).isNotNull()
        );
    }

    @DisplayName("JwtErrorCode 인스턴스 httpStatus() 테스트")
    @Test
    void httpStatus() {
        assertAll(
                () -> assertThat(JwtErrorCode.values(
                        new JwtAuthenticationException("test")).httpStatus()).isEqualTo(UNAUTHORIZED),
                () -> assertThat(JwtErrorCode.values(
                        new JwtProviderNotSupportTypeException("test")).httpStatus()).isEqualTo(UNAUTHORIZED),
                () -> assertThat(JwtErrorCode.values(
                        new JwtParseException()).httpStatus()).isEqualTo(FORBIDDEN)
        );
    }

    @DisplayName("JwtErrorCode 인스턴스 message() 테스트")
    @Test
    void message_test() {
        assertAll(
                () -> assertThat(JwtErrorCode.values(
                        new JwtAuthenticationException("test")).message()).isEqualTo("Unauthorized"),
                () -> assertThat(JwtErrorCode.values(
                        new JwtProviderNotSupportTypeException("test")).message()).isEqualTo("Unauthorized"),
                () -> assertThat(JwtErrorCode.values(
                        new JwtParseException()).message()).isEqualTo("Unauthorized")
        );
    }

}