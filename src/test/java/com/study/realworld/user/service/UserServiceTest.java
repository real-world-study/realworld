package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        // when
        User result = userService.findAll().get(0);

        // then
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

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        // when
        User result = userService.findById(ID);

        // then
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

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));

        // when
        User result = userService.findByEmail(user.getEmail());

        // then
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

        when(userRepository.save(any()))
                .thenReturn(user);

        // when
        User result = userService.save(request);

        // then
        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).save(user);
        assertAll(
                () -> assertEquals(request.getEmail(), result.getEmail()),
                () -> assertEquals(request.getUsername(), result.getUsername())
        );
    }

    @Test
    void deleteById() {

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(passwordEncoder.encode(PASSWORD))
                .bio(BIO)
                .image(IMAGE)
                .build();

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        //doNothing().when(userRepository).delete(any());

        // when
        User result = userService.deleteById(ID);

        // then
        verify(userRepository).findById(ID);
        verify(userRepository).delete(user);
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
    void deleteById_exception() {

        // given
        when(userRepository.findById(anyLong()))
                .thenThrow(NoSuchElementException.class);

        // when

        // then
        assertThrows(NoSuchElementException.class,
                () -> userService.deleteById(ID));
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

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(user));

        /**
         * This exception may occur if matchers are combined with raw values:
         *     //incorrect:
         *     someMethod(anyObject(), "raw String");
         * When using matchers, all arguments have to be provided by matchers.
         * For example:
         *     //correct:
         *     someMethod(anyObject(), eq("String by matcher"));
         *
         * For more info see javadoc for Matchers class.
         */
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        // when
        User result = userService.login(request);

        // then
        assertAll(
                () -> assertEquals(request.getEmail(), result.getEmail()),
                () -> assertTrue(result.matchesPassword(request.getPassword(), passwordEncoder))
        );
    }

    @Disabled
    @Test
    void login_exception_not_matchesPassword() {
        // TODO
    }

}
