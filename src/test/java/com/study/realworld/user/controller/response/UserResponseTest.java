package com.study.realworld.user.controller.response;

import static com.study.realworld.user.controller.response.UserResponse.fromUserAndToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import org.junit.jupiter.api.Test;

class UserResponseTest {

    @Test
    void userResponseTest() {
        UserResponse userResponse = new UserResponse();
    }

    @Test
    void userResponseFromUserTest() {

        // given
        User user = User.Builder()
            .username(new Username("username"))
            .email(new Email("test@test.com"))
            .bio(new Bio("bio"))
            .image(new Image("image"))
            .build();
        String token = "token";

        // when
        UserResponse result = fromUserAndToken(user, token);

        // then
        assertThat(result.getUsername()).isEqualTo("username");
        assertThat(result.getEmail()).isEqualTo("test@test.com");
        assertThat(result.getBio()).isEqualTo("bio");
        assertThat(result.getImage()).isEqualTo("image");
        assertThat(result.getToken()).isEqualTo("token");
    }

}