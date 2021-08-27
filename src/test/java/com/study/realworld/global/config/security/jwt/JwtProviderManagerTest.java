package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.auth.exception.JwtProviderNotSupportTokenException;
import com.study.realworld.domain.auth.exception.JwtProviderNotSupportTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.testToken;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.User.DEFAULT_AUTHORITY;
import static com.study.realworld.global.config.security.jwt.JwtAuthentication.initAuthentication;
import static com.study.realworld.global.config.security.jwt.JwtAuthentication.ofUserDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class JwtProviderManagerTest {

    @Mock private JwtProvider jwtProvider;
    @InjectMocks private JwtProviderManager jwtProviderManager;

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtProviderManager jwtProviderManager =
                new JwtProviderManager(jwtProvider);

        assertAll(
                () -> assertThat(jwtProviderManager).isNotNull(),
                () -> assertThat(jwtProviderManager)
                        .isExactlyInstanceOf(JwtProviderManager.class)
        );
    }

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 validateSupportable() 실패 테스트")
    @Test
    void fail_validateSupportable_test() {
        doReturn(false).when(jwtProvider).supports(any());

        final TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(USERNAME, PASSWORD);
        assertThatThrownBy(() -> jwtProviderManager.authenticate(testingAuthenticationToken))
                .isInstanceOf(JwtProviderNotSupportTypeException.class)
                .hasMessage("[ TestingAuthenticationToken ] is not supported in JwtProvider");
    }

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 validateToken() 실패 테스트")
    @Test
    void fail_validateToken_test() {
        doReturn(true).when(jwtProvider).supports(any());

        final JwtAuthentication jwtAuthentication = JwtAuthentication.initAuthentication("failToken");
        assertThatThrownBy(() -> jwtProviderManager.authenticate(jwtAuthentication))
                .isInstanceOf(JwtProviderNotSupportTokenException.class)
                .hasMessage("this token is not supported in JwtProvider");
    }

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 support() 테스트")
    @Test
    void authenticate_test() {
        final UserDetails userDetails = securityUser();
        final JwtAuthentication returnedAuthentication = ofUserDetails(userDetails);
        doReturn(returnedAuthentication).when(jwtProvider).authenticate(any());
        doReturn(true).when(jwtProvider).supports(any());
        doReturn(true).when(jwtProvider).validateToken(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(testToken());
        final Authentication authentication = jwtProviderManager.authenticate(jwtAuthentication);
        assertAll(
                () -> assertThat(authentication).isNotNull(),
                () -> assertThat(authentication.getPrincipal()).isEqualTo(USERNAME),
                () -> assertThat(authentication.getCredentials()).isEqualTo(PASSWORD)
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