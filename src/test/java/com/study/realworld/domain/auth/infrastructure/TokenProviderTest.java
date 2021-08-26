package com.study.realworld.domain.auth.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestPropertySource("classpath:application.yml")
class TokenProviderTest {

    private String testKey = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

    @DisplayName("TokenProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final TokenProvider tokenProvider = new TokenProvider(testKey);
        assertAll(
                () -> assertThat(tokenProvider).isNotNull(),
                () -> assertThat(tokenProvider).isExactlyInstanceOf(TokenProvider.class)
        );
    }

}