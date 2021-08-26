package com.study.realworld.global.config.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtAuthenticationProviderManagerTest {

    @Mock
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @InjectMocks
    private JwtAuthenticationProviderManager jwtAuthenticationProviderManager;

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtAuthenticationProviderManager jwtAuthenticationProviderManager =
                new JwtAuthenticationProviderManager(jwtAuthenticationProvider);

        assertAll(
                () -> assertThat(jwtAuthenticationProviderManager).isNotNull(),
                () -> assertThat(jwtAuthenticationProviderManager)
                        .isExactlyInstanceOf(JwtAuthenticationProviderManager.class)
        );

    }

}