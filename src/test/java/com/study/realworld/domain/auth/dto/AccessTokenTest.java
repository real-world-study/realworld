package com.study.realworld.domain.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AccessTokenTest {

    @DisplayName("AccessToken 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AuthToken accessToken = new AccessToken();

        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class)
        );
    }

}