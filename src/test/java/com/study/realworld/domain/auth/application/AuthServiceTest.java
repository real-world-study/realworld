package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.util.TestPasswordEncoder;
import com.study.realworld.domain.user.error.exception.PasswordMissMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.study.realworld.domain.user.domain.persist.UserTest.testDefaultUser;
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
        final User user = testDefaultUser().encode(passwordEncoder);
        willReturn(user).given(userQueryService).findByMemberEmail(any());

        final User login = authService.login(user.userEmail(), testDefaultUser().userPassword());
        assertThat(login).isEqualTo(user);
    }

    @Test
    void 로그인_실패() {
        final User user = testDefaultUser();
        willReturn(user).given(userQueryService).findByMemberEmail(any());

        assertThatThrownBy(() -> authService.login(user.userEmail(), user.userPassword()))
                .isExactlyInstanceOf(PasswordMissMatchException.class)
                .hasMessage("패스워드가 일치하지 않습니다.");
    }
}