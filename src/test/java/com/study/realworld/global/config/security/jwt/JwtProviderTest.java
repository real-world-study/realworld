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
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.TEST_TOKEN;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.User.DEFAULT_AUTHORITY;
import static com.study.realworld.global.config.security.jwt.JwtAuthentication.initAuthentication;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    @Mock private TokenProvider tokenProvider;
    @Mock private JwtUserDetailsService jwtUserDetailsService;
    @InjectMocks private JwtProvider jwtProvider;

    @DisplayName("JwtAuthenticationProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtProvider jwtProvider =
                new JwtProvider(jwtUserDetailsService, tokenProvider);

        assertAll(
                () -> assertThat(jwtProvider).isNotNull(),
                () -> assertThat(jwtProvider).isInstanceOf(AuthenticationProvider.class),
                () -> assertThat(jwtProvider).isExactlyInstanceOf(JwtProvider.class)
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 authenticate() 테스트")
    @Test
    void authenticate_test() {
        final UserDetails userDetails = securityUser();
        doReturn(USERNAME).when(tokenProvider).mapToUsername(any());
        doReturn(userDetails).when(jwtUserDetailsService).loadUserByUsername(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(TEST_TOKEN);
        final Authentication authenticate = jwtProvider.authenticate(jwtAuthentication);

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
                () -> assertThat(jwtProvider.supports(jwtAuthentication.getClass())).isTrue(),
                () -> assertThat(jwtProvider.supports(testingAuthenticationToken.getClass())).isFalse()
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 validateToken() 테스트")
    @Test
    void validate_test() {
        doReturn(false).when(tokenProvider).validateToken(any());
        doReturn(true).when(tokenProvider).validateToken(TEST_TOKEN);

        assertAll(
                () -> assertThat(jwtProvider.validateToken(TEST_TOKEN)).isTrue(),
                () -> assertThat(jwtProvider.validateToken("failToken")).isFalse()
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 validateUserDetailsNull() 테스트")
    @Test
    void validateUserDetailsNull_test() {
        doReturn(USERNAME).when(tokenProvider).mapToUsername(any());
        doReturn(null).when(jwtUserDetailsService).loadUserByUsername(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(TEST_TOKEN);
        assertThatThrownBy(() -> jwtProvider.authenticate(jwtProvider.authenticate(jwtAuthentication)))
                .isInstanceOf(InternalAuthenticationServiceException.class)
                .hasMessage("UserDetailsService returned null, which is an interface contract violation");
    }

    private UserDetails securityUser() {
        return User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(DEFAULT_AUTHORITY)
                .build();
    }

}