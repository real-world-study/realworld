package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.auth.application.JwtUserDetailsService;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderTest {

    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;
    @InjectMocks
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @DisplayName("JwtAuthenticationProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtAuthenticationProvider jwtAuthenticationProvider =
                new JwtAuthenticationProvider(jwtUserDetailsService, tokenProvider);

        assertAll(
                () -> assertThat(jwtAuthenticationProvider).isNotNull(),
                () -> assertThat(jwtAuthenticationProvider).isInstanceOf(AuthenticationProvider.class),
                () -> assertThat(jwtAuthenticationProvider).isExactlyInstanceOf(JwtAuthenticationProvider.class)
        );
    }
}