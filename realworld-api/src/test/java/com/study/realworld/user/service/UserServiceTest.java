package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import com.study.realworld.user.entity.User;
import com.study.realworld.user.jwt.TokenDto;
import com.study.realworld.user.jwt.TokenProvider;
import com.study.realworld.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenProvider tokenProvider;

    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @BeforeEach
    void setUp() {
    }

    private static final String username = "DolphaGo";
    private static final String email = "dolphago@test.net";
    private static final String password = "1%^$asd^&!@$";
    private static final String image = "/test/aaa.jpg";
    private static final String bio = "Hello, world";

    private User getUser() {
        return User.builder()
                   .username(username)
                   .email(email)
                   .password(password)
                   .bio(bio)
                   .image(image)
                   .build();
    }

    private TokenDto getTokenDto() {
        return TokenDto.builder()
                       .accessToken("accessToken")
                       .refreshToken("refreshToken")
                       .build();
    }

    @DisplayName("Login Test")
    @Nested
    class Login {

        @DisplayName("Password correct")
        @Test
        void login_correct() {
            final User user = getUser();
            final TokenDto tokenDto = getTokenDto();
            final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
            final Authentication auth = mock(Authentication.class);

            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

            final User loginUser = userService.login(user);

            assertAll(
                    () -> assertThat(loginUser.getUsername()).isEqualTo(user.getUsername()),
                    () -> assertThat(loginUser.getEmail()).isEqualTo(user.getEmail()),
                    () -> assertThat(loginUser.getPassword()).isEqualTo(user.getPassword())
            );
        }

        @DisplayName("Password wrong")
        @Test
        void login_wrong() {
            final User user = getUser();
            final User mockUser = mock(User.class);

            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> userService.login(mockUser))
                    .withMessage("Password is wrong.");
        }

    }

    @DisplayName("Join Test")
    @Nested
    class Join {

        @DisplayName("Normal Join Situation")
        @Test
        void join() {
            User user = getUser();
            when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
            userService.join(user);
        }

        @DisplayName("DuplicatedEmail Situation")
        @Test
        void duplicated_email() {
            User user = getUser();
            when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> userService.join(user))
                    .withMessage("This user already exists.");
        }
    }

    @DisplayName("Update Test")
    @Nested
    class Update {

        @DisplayName("Normal Update - bio/image update")
        @Test
        void update() {
            User user = getUser();
            when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

            User updateUser = User.builder()
                                  .email(user.getEmail())
                                  .bio("jordy")
                                  .image("/path/test.png")
                                  .build();

            final User result = userService.update(updateUser);

            assertAll(
                    () -> assertThat(result.getBio()).isEqualTo(updateUser.getBio()),
                    () -> assertThat(result.getImage()).isEqualTo(updateUser.getImage())
            );
        }

    }
}
