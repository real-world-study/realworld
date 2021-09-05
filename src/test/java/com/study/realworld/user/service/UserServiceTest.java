package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.dto.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll() {
        // given
        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.findAll())
                .thenReturn(List.of(user));

        // then
        User result = userService.findAll().get(0);

        verify(userRepository).findAll();
        assertAll(
                () -> assertEquals(user.getEmail(), result.getEmail()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(user.getPassword(), result.getPassword()),
                () -> assertEquals(user.getBio(), result.getBio()),
                () -> assertEquals(user.getImage(), result.getImage())
        );
    }

    @Test
    void findById() {
        // given
        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        // then
        User result = userService.findById(ID);

        verify(userRepository).findById(ID);
        assertAll(
                () -> assertEquals(user.getEmail(), result.getEmail()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(user.getPassword(), result.getPassword()),
                () -> assertEquals(user.getBio(), result.getBio()),
                () -> assertEquals(user.getImage(), result.getImage())
        );
    }

    @Test
    void findByEmail() {
        // given
        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));

        // then
        User result = userService.findByEmail(user.getEmail());

        verify(userRepository).findByEmail(user.getEmail());
        assertAll(
                () -> assertEquals(user.getId(), result.getId()),
                () -> assertEquals(user.getEmail(), result.getEmail()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(user.getPassword(), result.getPassword()),
                () -> assertEquals(user.getBio(), result.getBio()),
                () -> assertEquals(user.getImage(), result.getImage())
        );
    }

    @Test
    void save() {
        // given
        final UserJoinRequest request = UserJoinRequest.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(passwordEncoder.encode(PASSWORD))
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.save(any()))
                .thenReturn(user);

        // then
        User result = userService.save(request);

        verify(userRepository).save(user);
        assertAll(
                () -> assertEquals(request.getEmail(), result.getEmail()),
                () -> assertEquals(request.getUsername(), result.getUsername())
        );
    }

    @Test
    void save_validateDuplicateUser_exception() {
        // given
        final UserJoinRequest request = UserJoinRequest.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));

        // then
        assertThrows(DuplicateKeyException.class, () -> userService.save(request));
    }

    @Test
    void login() {
        // given
        final UserLoginRequest request = UserLoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        // then
        User result = userService.login(request);

        assertAll(
                () -> assertEquals(request.getEmail(), result.getEmail()),
                () -> assertTrue(result.matchesPassword(request.getPassword(), passwordEncoder))
        );
    }

    @Test
    void login_validateMatchesPassword_exception() {
        // given
        final UserLoginRequest request = UserLoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));

        // then
        assertThrows(NoSuchElementException.class, () -> userService.login(request));
    }

    @Test
    void update() {
        // given
        UserUpdateRequest request = UserUpdateRequest.builder()
                .email(EMAIL+EMAIL)
                .password(PASSWORD+PASSWORD)
                .bio(BIO+BIO)
                .build();

        User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));

        // then
        User result = userService.update(request, EMAIL);

        assertAll(
                () -> assertEquals(request.getEmail(), result.getEmail()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(request.getBio(), result.getBio()),
                () -> assertEquals(user.getImage(), result.getImage())
        );
    }

    @Test
    void update_validateExistUser_exception() {
        // given
        UserUpdateRequest request = UserUpdateRequest.builder()
                .email(EMAIL+EMAIL)
                .password(PASSWORD+PASSWORD)
                .bio(BIO+BIO)
                .build();

        // when
        when(userRepository.existsByEmail(anyString()))
                .thenReturn(true);

        // then
        assertThrows(DuplicateKeyException.class, () -> userService.update(request, EMAIL));
    }

}
