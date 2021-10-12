package com.study.realworld.user.controller.response;

import static com.study.realworld.user.controller.response.UserResponse.fromUserAndToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
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
            .profile(Profile.Builder()
                .username(Username.of("username"))
                .bio(Bio.of("bio"))
                .image(Image.of("image"))
                .build())
            .email(Email.of("test@test.com"))
            .build();
        String token = "token";

        // when
        UserResponse result = fromUserAndToken(user, token);

        // then
        assertThat(result.getUsername()).isEqualTo(Username.of("username"));
        assertThat(result.getEmail()).isEqualTo(Email.of("test@test.com"));
        assertThat(result.getBio()).isEqualTo(Bio.of("bio"));
        assertThat(result.getImage()).isEqualTo(Image.of("image"));
        assertThat(result.getToken()).isEqualTo("token");
    }

}