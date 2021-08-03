package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void save_and_findAll() {

        // given
        User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        userService.save(user);

        // when
        List<User> userList = userService.findAll();

        // then
        User userTest = userList.get(0);
        assertAll(
                () -> assertEquals(user.getId(), userTest.getId()),
                () -> assertEquals(user.getEmail(), userTest.getEmail()),
                () -> assertEquals(user.getUsername(), userTest.getUsername()),
                () -> assertEquals(user.getPassword(), userTest.getPassword()),
                () -> assertEquals(user.getBio(), userTest.getBio()),
                () -> assertEquals(user.getImage(), userTest.getImage())
        );
    }

    @Test
    void save_and_findByEmail() {

        // given
        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        userService.save(user);

        // when
        User userTest = userService.findByEmail(user.getEmail());

        // then
        assertAll(
                () -> assertEquals(user.getId(), userTest.getId()),
                () -> assertEquals(user.getEmail(), userTest.getEmail()),
                () -> assertEquals(user.getUsername(), userTest.getUsername()),
                () -> assertEquals(user.getPassword(), userTest.getPassword()),
                () -> assertEquals(user.getBio(), userTest.getBio()),
                () -> assertEquals(user.getImage(), userTest.getImage())
        );
    }

    @Test
    void save_and_findById() {

        // given
        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        userService.save(user);

        // when
        User userTest = userService.findById(user.getId());

        // then
        assertAll(
                () -> assertEquals(user.getId(), userTest.getId()),
                () -> assertEquals(user.getEmail(), userTest.getEmail()),
                () -> assertEquals(user.getUsername(), userTest.getUsername()),
                () -> assertEquals(user.getPassword(), userTest.getPassword()),
                () -> assertEquals(user.getBio(), userTest.getBio()),
                () -> assertEquals(user.getImage(), userTest.getImage())
        );
    }

    @Test
    void save_and_deleteById() {

        // given
        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        userService.save(user);

        // when
        User userTest = userService.deleteById(user.getId());

        // then
        assertAll(
                () -> assertEquals(user.getId(), userTest.getId()),
                () -> assertEquals(user.getEmail(), userTest.getEmail()),
                () -> assertEquals(user.getUsername(), userTest.getUsername()),
                () -> assertEquals(user.getPassword(), userTest.getPassword()),
                () -> assertEquals(user.getBio(), userTest.getBio()),
                () -> assertEquals(user.getImage(), userTest.getImage())
        );
    }

    @Test
    void save_and_deleteById_exception() {

        // given
        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        userService.save(user);

        // when
        final Long newId = ID + ID;

        // then
        assertThrows(UserNotFoundException.class,
                () -> userService.deleteById(newId));
    }

}