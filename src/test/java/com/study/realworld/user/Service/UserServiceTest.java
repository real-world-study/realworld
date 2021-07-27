package com.study.realworld.user.Service;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("이미 존재하는 유저네임이 들어왔을 때 Exception이 발생되어야 한다.")
    void existUsernameJoinTest() {

        // setup
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User.Builder().build()));

        // given
        User user = new User.Builder().build();

        // given & when
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> userService.join(user));
    }

    @Test
    @DisplayName("이미 존재하는 이메일이 들어왔을 때 Exception이 발생되어야 한다.")
    void existEmailJoinTest() {

        // setup
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User.Builder().build()));

        // given
        User user = new User.Builder().build();

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
        User input = new User.Builder()
                .username(new Username("username"))
                .email(new Email("email"))
                .bio("bio")
                .image("image")
                .build();
        when(userRepository.save(input)).thenReturn(
                new User.Builder()
                        .id(1L)
                        .username(new Username("username"))
                        .email(new Email("email"))
                        .bio("bio")
                        .image("image")
                        .build()
        );

        // when
        User user = userService.join(input);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo(new Username("username"));
        assertThat(user.getEmail()).isEqualTo(new Email("email"));
        assertThat(user.getBio()).isEqualTo("bio");
        assertThat(user.getImage()).isEqualTo("image");
    }

}