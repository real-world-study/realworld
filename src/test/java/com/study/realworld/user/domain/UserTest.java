package com.study.realworld.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void userTest() {
        User user = new User();
    }

    @Test
    void userBuilderTest() {

        // given
        Long id = 1L;
        Username username = new Username("username");
        Email email = new Email("email@email.com");
        Password password = new Password("password");
        String bio = "bio";
        String image = "image.jpg";

        // when
        User user = new User.Builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .bio(bio)
                .image(image)
                .build();

        // then
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getBio()).isEqualTo(bio);
        assertThat(user.getImage()).isEqualTo(image);
    }

}
