package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("이미 존재하는 유저네임이 들어왔을 때 Exception이 발생되어야 한다.")
    void existUsernameJoinTest() {

        // setup & given
        User user = User.Builder().build();
        when(userRepository.findByUsername(any()))
            .thenReturn(Optional.of(user));

        // when & then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> userService.join(user));
    }

    @Test
    @DisplayName("이미 존재하는 이메일이 들어왔을 때 Exception이 발생되어야 한다.")
    void existEmailJoinTest() {

        // setup & given
        User user = User.Builder().build();
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        // when & then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> userService.join(user));
    }

    @Test
    @DisplayName("새로운 유저가 왔을 때 정상적으로 해당 유저가 저장되고 유저 유저정보가 반환되어야 한다.")
    void successJoinTest() {

        // setup & given
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        Password password = new Password("password");
        when(passwordEncoder.encode(password.getPassword())).thenReturn("encoded_password");
        User input = User.Builder()
            .username(new Username("username"))
            .email(new Email("email"))
            .password(password)
            .bio("bio")
            .image("image")
            .build();
        when(userRepository.save(any())).thenReturn(
            User.Builder()
                .id(1L)
                .username(input.getUsername())
                .email(input.getEmail())
                .password(new Password("encoded_password"))
                .bio(input.getBio())
                .image(input.getImage())
                .build()
        );

        // when
        User user = userService.join(input);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo(new Username("username"));
        assertThat(user.getEmail()).isEqualTo(new Email("email"));
        assertThat(user.getPassword().getPassword())
            .isEqualTo("encoded_password");
        assertThat(user.getBio()).isEqualTo("bio");
        assertThat(user.getImage()).isEqualTo("image");
    }

    @Test
    @DisplayName("로그인 요청한 이메일이 존재하지 않는 이메일이면 Exception을 반환해야 한다.")
    void loginFailByEmailTest() {

        // setup & given
        Email email = new Email("test@test.com");
        Password password = new Password("password");
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when && then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> userService.login(email, password));
    }

    @Test
    @DisplayName("로그인 요청한 이메일에 매칭되지 않는 패스워드이면 Exception을 반환해야 한다.")
    void loginFailByPasswordTest() {

        // setup & given
        Email email = new Email("test@test.com");
        Password password = new Password("password");
        User user = User.Builder().password(new Password("encoded_password")).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(false);

        // when & then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> userService.login(email, password));
    }

    @Test
    @DisplayName("로그인 요청한 이메일 페스워드가 실제 유저일 때 유저를 반환한다.")
    void loginSuccessTest() {

        // setup & given
        Email email = new Email("test@test.com");
        Password password = new Password("password");
        User input = User.Builder().email(email).password(new Password("encoded_password")).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(input));
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);

        // when
        User user = userService.login(email, password);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getEmail().toString()).isEqualTo("test@test.com");
        assertThat(user.getPassword().getPassword()).isEqualTo("encoded_password");
    }

}