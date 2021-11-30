package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.error.exception.PasswordMissMatchException;
import com.study.realworld.domain.user.util.TestPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("인증 서비스(AuthService)")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserQueryService userQueryService;

    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        passwordEncoder = TestPasswordEncoder.initialize();
        authService = new AuthService(userQueryService, passwordEncoder);
    }

    @Test
    void 로그인_성공() {
        final User user = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE)
                .encode(passwordEncoder);

        willReturn(user).given(userQueryService).findByMemberEmail(any());

        final User loginUser = authService.login(user.userEmail(), USER_PASSWORD);
        assertThat(loginUser).isEqualTo(user);
    }

    @Test
    void 로그인_실패() {
        final User user = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        willReturn(user).given(userQueryService).findByMemberEmail(any());

        assertThatThrownBy(() -> authService.login(user.userEmail(), user.userPassword()))
                .isExactlyInstanceOf(PasswordMissMatchException.class)
                .hasMessage("패스워드가 일치하지 않습니다.");
    }
}
