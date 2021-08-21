package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void joinSuccessTest() {

        // setup & given
        User user = User.builder().build();
        when(userRepository.save(any())).thenReturn(user);

        // when
        User result = userService.join(user);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void loginSuccessTest() {

        // setup & given
        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .build();
        String email = "test@test.com";
        String password = "password";
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));

        // when
        User result = userService.login(email, password);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void loginFailByEmailTest() {

        // setup & given
        User user = User.builder().build();
        String email = "test@test.com";
        String password = "password";
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> userService.login(email, password));
    }

    @Test
    void loginFailByPasswordTest() {

        // setup & given
        User user = User.builder().email("test@test.com").password("password").build();
        String email = "test@test.com";
        String password = "password1";
        when(userRepository.findByEmail(email))
            .thenReturn(Optional.ofNullable(user));

        // when & then
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> userService.login(email, password));
    }

}