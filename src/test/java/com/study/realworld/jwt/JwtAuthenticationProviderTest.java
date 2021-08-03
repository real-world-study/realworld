package com.study.realworld.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderTest {

    @Mock
    private Authentication authentication;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private JwtAuthenticationProvider provider;

    private Email email;
    private Password password;

    @BeforeEach
    void beforeEach() {
        email = new Email("test@test.com");
        password = new Password("password");
    }

    @Test
    @DisplayName("JwtAuthenticationToken class support test")
    void supportSuccessTest() {

        // given & when
        boolean result = provider.supports(JwtAuthenticationToken.class);

        // then
        assertTrue(result);
    }

    @Test
    void supportFailTest() {

        // given & when
        boolean result = provider.supports(provider.getClass());

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("받은 아이디 비밀번호가 다르면 Exception이 발생해야한다.")
    void authenticationByErrorEmailAndPasswordTest() {

        // setup & given
        when(userService.login(any(), any())).thenThrow();

        // when & then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> provider.authenticate(authentication));
    }

    @Test
    @DisplayName("로그인 정보가 일치한다면 accessToken과 user가 details필드에 포함되어 authentication이 반환되어야 한다.")
    void authenticateSuccessTest() {

        // setup & given
        User user = User.Builder().build();
        when(userService.login(any(), any())).thenReturn(user);
        when(tokenProvider.generateToken(any())).thenReturn("token");

        // when
        Authentication result = provider.authenticate(authentication);
        AuthenticationResponse details = (AuthenticationResponse) result.getDetails();

        // then
        assertThat(result).isNotNull();
        assertThat(details.getAccessToken()).isEqualTo("token");
        assertThat(details.getUser()).isEqualTo(user);
    }
}