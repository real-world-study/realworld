package com.study.realworld.user.domain;

import org.junit.jupiter.api.DisplayName;
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

    @Test
    void userBuilderParamUserTest() {

        // given
        User input = new User.Builder()
            .id(1L)
            .username(new Username("username"))
            .email(new Email("email"))
            .password(new Password("password"))
            .bio("bio")
            .image("image")
            .build();

        // when
        User user = new User.Builder(input).build();

        // then
        assertThat(user.getId()).isEqualTo(input.getId());
        assertThat(user.getUsername()).isEqualTo(input.getUsername());
        assertThat(user.getEmail()).isEqualTo(input.getEmail());
        assertThat(user.getPassword()).isEqualTo(input.getPassword());
        assertThat(user.getBio()).isEqualTo(input.getBio());
        assertThat(user.getImage()).isEqualTo(input.getImage());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void userEqualsHashCodeTest() {

        // given
        User user = new User.Builder().email(new Email("test@test.com")).build();
        User copyUser = new User.Builder().email(new Email("test@test.com")).build();

        // when & then
        assertThat(user)
            .isEqualTo(copyUser)
            .hasSameHashCodeAs(copyUser);
    }

}
