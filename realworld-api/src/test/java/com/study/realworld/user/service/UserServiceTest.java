package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.study.realworld.exception.CustomException;
import com.study.realworld.user.controller.dto.request.JoinDto;
import com.study.realworld.user.controller.dto.request.LoginDto;
import com.study.realworld.user.controller.dto.request.UpdateDto;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.entity.User;
import com.study.realworld.user.jwt.TokenProvider;
import com.study.realworld.user.repository.UserRepository;
import com.study.realworld.util.SecurityUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    private User getUser(String email) {
        return User.builder()
                   .username("DolphaGo")
                   .email(email)
                   .password("1%^$asd^&!@$")
                   .bio("Hello, world")
                   .image("/test/aaa.jpg")
                   .build();
    }

    @DisplayName("Login Test")
    @Nested
    class Login {

        @DisplayName("Password correct")
        @Test
        void login_correct() {
            final LoginDto loginDto = LoginDto.create("dolphago@test.net", "1q2w3e4r");
            User user = getUser("dolphago@test.net");

            when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
            when(tokenProvider.createToken(any(Authentication.class))).thenReturn("token-123");
            final UserInfo loginUser = userService.login(loginDto);

            assertAll(
                    () -> assertThat(loginUser.getUsername()).isEqualTo(user.getUsername()),
                    () -> assertThat(loginUser.getEmail()).isEqualTo(user.getEmail()),
                    () -> assertThat(loginUser.getAccessToken()).isEqualTo("token-123")
            );
        }

        @DisplayName("Password wrong")
        @Test
        void login_wrong() {
            final LoginDto loginDto = LoginDto.create("dolphago@test.net", "1q2w3e4r");
            User user = getUser("dolphago@test.net");

            when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);
            assertThatExceptionOfType(CustomException.class)
                    .isThrownBy(() -> userService.login(loginDto));
        }

    }

    @DisplayName("Join Test")
    @Nested
    class Join {

        @DisplayName("Normal Join Situation")
        @Test
        void join() {
            final JoinDto joinDto = JoinDto.create("dolphago@test.net", "DolphaGo", "1q2w3e4r");
            User user = getUser("dolphago@test.net");

            when(userRepository.findByEmail(joinDto.getEmail())).thenReturn(Optional.empty());
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
            when(tokenProvider.createToken(any())).thenReturn("token-123");

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            final UserInfo joinUser = userService.join(joinDto);
            verify(userRepository, times(1)).save(captor.capture());

            assertAll(
                    () -> assertThat(joinUser.getUsername()).isEqualTo(user.getUsername()),
                    () -> assertThat(joinUser.getEmail()).isEqualTo(user.getEmail()),
                    () -> assertThat(joinUser.getAccessToken()).isEqualTo("token-123"),
                    () -> assertThat(captor.getValue().getPassword()).isEqualTo("encodedPassword")
            );
        }

        @DisplayName("DuplicatedEmail Situation")
        @Test
        void duplicated_email() {
            JoinDto dto = JoinDto.create("dolphago@test.net", "DolphaGo", "1q2w3e4r");
            when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(User.builder().build()));
            assertThatExceptionOfType(CustomException.class).isThrownBy(() -> userService.join(dto));
        }
    }

    @DisplayName("Update Test")
    @Nested
    class Update {

        @DisplayName("Normal Update - bio/image update")
        @Test
        void update() {
            final UpdateDto updateDto = UpdateDto.create("dolphago@test.net", "DolphaGo", "1q2w3e4r");
            User user = getUser("dolphago@test.net");

            when(userRepository.findByEmail(updateDto.getEmail())).thenReturn(Optional.of(user));
            when(tokenProvider.createToken(any())).thenReturn("renew-token");

            final UserInfo updateUser = userService.update(updateDto, user.getEmail());

            assertAll(
                    () -> assertThat(updateUser.getUsername()).isEqualTo(user.getUsername()),
                    () -> assertThat(updateUser.getEmail()).isEqualTo(user.getEmail()),
                    () -> assertThat(updateUser.getAccessToken()).isEqualTo("renew-token"),
                    () -> assertThat(updateUser.getBio()).isEqualTo(updateDto.getBio()),
                    () -> assertThat(updateUser.getImage()).isEqualTo(updateDto.getImage())
            );
        }
    }
}
