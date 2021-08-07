package com.study.realworld.user.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    @Test
    void user_builder() {

        // when
        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // then
        assertAll("Should be equal with User's properties",
                () -> assertEquals(ID, user.getId()),
                () -> assertEquals(EMAIL, user.getEmail()),
                () -> assertEquals(USERNAME, user.getUsername()),
                () -> assertEquals(PASSWORD, user.getPassword()),
                () -> assertEquals(BIO, user.getBio()),
                () -> assertEquals(IMAGE, user.getImage())
        );
    }

    @Test
    void user_equals_and_hashCode() {

        // when
        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        final User userTest = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // then
        assertTrue(user.equals(userTest));
        assertEquals(user.hashCode(), userTest.hashCode());
    }

}
