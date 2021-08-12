package com.study.realworld.domain.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AccessTokenTest {

    @DisplayName("AccessToken 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AccessToken accessToken = new AccessToken();

        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class)
        );
    }

    @DisplayName("AccessToken 인스턴스 정적 팩토리 메서드 테스트")
    @Test
    void static_factory_method_test() {
        final String accessTokenString = "accessToken";
        final AccessToken accessToken = AccessToken.ofString(accessTokenString);

        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class)
        );
    }

    @DisplayName("AccessToken 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final String accessTokenString = "accessToken";
        final AccessToken accessToken = AccessToken.ofString(accessTokenString);

        assertThat(accessToken.accessToken()).isEqualTo(accessTokenString);
    }

}