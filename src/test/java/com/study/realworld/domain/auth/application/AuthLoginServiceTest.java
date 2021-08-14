package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthLoginServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthLoginService authLoginService;

    @DisplayName("AuthLoginService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AuthLoginService authLoginService = new AuthLoginService(userRepository);

        assertAll(
                () -> assertThat(authLoginService).isNotNull(),
                () -> assertThat(authLoginService).isExactlyInstanceOf(AuthLoginService.class)
        );
    }

}