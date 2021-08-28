package com.study.realworld.domain.user.application;

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
class UserUpdateServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserUpdateService userUpdateService;

    @DisplayName("UserUpdateService 생성자 테스트")
    @Test
    void constructor_test() {
        final UserUpdateService userUpdateService = new UserUpdateService(userRepository);

        assertAll(
                () -> assertThat(userUpdateService).isNotNull(),
                () -> assertThat(userUpdateService).isExactlyInstanceOf(UserUpdateService.class)
        );
    }

}