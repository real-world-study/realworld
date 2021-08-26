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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.TEST_TOKEN;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.User.DEFAULT_AUTHORITY;
import static com.study.realworld.global.config.security.jwt.JwtAuthentication.initAuthentication;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

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

    @DisplayName("JwtAuthenticationProvider 인스턴스 authenticate() 테스트")
    @Test
    void authenticate_test() {
        final UserDetails userDetails = securityUser();
        doReturn(USERNAME).when(tokenProvider).mapToUsername(any());
        doReturn(userDetails).when(jwtUserDetailsService).loadUserByUsername(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(TEST_TOKEN);
        final Authentication authenticate = jwtAuthenticationProvider.authenticate(jwtAuthentication);

        assertAll(
                () -> assertThat(authenticate).isNotNull(),
                () -> assertThat(authenticate.getPrincipal()).isEqualTo(USERNAME),
                () -> assertThat(authenticate.getCredentials()).isEqualTo(PASSWORD)
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 supports() 테스트")
    @Test
    void supports_test() {
        final JwtAuthentication jwtAuthentication = JwtAuthentication.initAuthentication(TEST_TOKEN);
        final TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(USERNAME, PASSWORD);

        assertAll(
                () -> assertThat(jwtAuthenticationProvider.supports(jwtAuthentication.getClass())).isTrue(),
                () -> assertThat(jwtAuthenticationProvider.supports(testingAuthenticationToken.getClass())).isFalse()
        );
    }

    private UserDetails securityUser() {
        return User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(DEFAULT_AUTHORITY)
                .build();
    }

}