package com.study.realworld.domain.auth.application.presentation;

import com.study.realworld.domain.auth.application.AuthLoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock private AuthLoginService authLoginService;
    @InjectMocks private AuthController authController;

    @DisplayName("AuthController 인스턴스 생성 여부 테스트")
    @Test
    void constructor_test() {
        final AuthController authController = new AuthController(authLoginService);

        assertAll(
                () -> assertThat(authController).isNotNull(),
                () -> assertThat(authController).isExactlyInstanceOf(AuthController.class)
        );
    }

}