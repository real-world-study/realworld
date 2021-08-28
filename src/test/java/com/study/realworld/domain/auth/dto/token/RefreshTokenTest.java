package com.study.realworld.domain.auth.dto.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RefreshTokenTest {
    public static final String REFRESH_TOKEN = "refreshToken";

    @DisplayName("RefreshToken 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final RefreshToken refreshToken = new RefreshToken();

        assertAll(
                () -> assertThat(refreshToken).isNotNull(),
                () -> assertThat(refreshToken).isExactlyInstanceOf(RefreshToken.class)
        );
    }

    @DisplayName("RefreshToken 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final String refreshTokenString = "refreshToken";
        final RefreshToken refreshToken = new RefreshToken(refreshTokenString);

        assertAll(
                () -> assertThat(refreshToken).isNotNull(),
                () -> assertThat(refreshToken).isExactlyInstanceOf(RefreshToken.class)
        );
    }

    @DisplayName("RefreshToken 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final String refreshTokenString = "refreshToken";
        final RefreshToken refreshToken = new RefreshToken(refreshTokenString);

        assertThat(refreshToken.refreshToken()).isEqualTo(refreshTokenString);
    }

}