package com.study.realworld.user.controller.response;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import org.junit.jupiter.api.Test;

import static com.study.realworld.user.controller.response.UserResponse.fromUser;
import static org.assertj.core.api.Assertions.assertThat;

class UserResponseTest {

    @Test
    void userResponseFromUserTest() {

        // given
        User user = new User.Builder()
                .username(new Username("username"))
                .email(new Email("test@test.com"))
                .bio("bio")
                .image("image")
                .build();

        // when
        UserResponse result = fromUser(user);

        // then
        assertThat(result.getUsername()).isEqualTo("username");
        assertThat(result.getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBio()).isEqualTo("bio");
        assertThat(result.getImage()).isEqualTo("image");
    }

}