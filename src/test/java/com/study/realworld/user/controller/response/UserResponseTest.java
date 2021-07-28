package com.study.realworld.user.controller.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        User user = User.Builder()
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

    @Test
    void userResponseJacksonTest() throws JsonProcessingException {

        // given
        UserResponse userResponse = new UserResponse(
            "username", "test@test.com", "bio", "image"
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
                + "\"}}"
        );
    }

}