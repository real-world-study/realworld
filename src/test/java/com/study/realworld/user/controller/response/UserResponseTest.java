package com.study.realworld.user.controller.response;

import static com.study.realworld.user.controller.response.UserResponse.fromUserAndToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import org.junit.jupiter.api.Test;

class UserResponseTest {

    @Test
    void userResponseFromUserTest() {

        // given
        User user = User.Builder()
            .username(new Username("username"))
            .email(new Email("test@test.com"))
            .bio("bio")
            .image("image")
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

    @Test
    void userResponseJacksonTest() throws JsonProcessingException {

        // given
        UserResponse userResponse = new UserResponse(
            "username", "test@test.com", "bio", "image", "token"
        );
        ObjectMapper objectMapper = new ObjectMapper();

        // when
        String result = objectMapper.writeValueAsString(userResponse);

        // then
        assertThat(result).isEqualTo(
            "{\"user\":{\"username\":\"" + userResponse.getUsername()
                + "\",\"email\":\"" + userResponse.getEmail()
                + "\",\"bio\":\"" + userResponse.getBio()
                + "\",\"image\":\"" + userResponse.getImage()
                + "\",\"token\":\"" + userResponse.getToken()
                + "\"}}"
        );
    }

}