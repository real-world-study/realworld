package com.study.realworld.domain.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.auth.dto.token.AccessTokenTest.ACCESS_TOKEN;
import static com.study.realworld.domain.auth.dto.token.RefreshTokenTest.REFRESH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResponseTokenTest {

    @DisplayName("ResponseToken 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final ResponseToken responseToken = new ResponseToken();

        assertAll(
                () -> assertThat(responseToken).isNotNull(),
                () -> assertThat(responseToken).isExactlyInstanceOf(ResponseToken.class)
        );
    }

    @DisplayName("ResponseToken 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final ResponseToken responseToken = new ResponseToken(ACCESS_TOKEN, REFRESH_TOKEN);

        assertAll(
                () -> assertThat(responseToken).isNotNull(),
                () -> assertThat(responseToken).isExactlyInstanceOf(ResponseToken.class)
        );
    }

}