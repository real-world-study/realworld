package com.study.realworld.global.config.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.TEST_TOKEN;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.User.DEFAULT_AUTHORITY;
import static com.study.realworld.global.config.security.jwt.JwtAuthentication.initAuthentication;
import static com.study.realworld.global.config.security.jwt.JwtAuthentication.ofUserDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
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

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 authenticate() 테스트")
    @Test
    void authenticate_test() {
        final UserDetails userDetails = securityUser();
        final JwtAuthentication returnedAuthentication = ofUserDetails(userDetails);
        doReturn(returnedAuthentication).when(jwtAuthenticationProvider).authenticate(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(TEST_TOKEN);
        final Authentication authentication = jwtAuthenticationProviderManager.authenticate(jwtAuthentication);
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