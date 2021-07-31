package com.study.realworld.user.controller.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.user.domain.User;
import org.junit.jupiter.api.Test;

import static com.study.realworld.user.controller.request.UserJoinRequest.from;
import static org.assertj.core.api.Assertions.assertThat;

class UserJoinRequestTest {

    @Test
    void userJoinRequestFromTest() {

        // given
        UserJoinRequest userJoinRequest = new UserJoinRequest(
            "username", "test@test.com", "password", "bio", "image"
        );

        // when
        User user = from(userJoinRequest);

        // then
        assertThat(user.getUsername().toString()).isEqualTo(userJoinRequest.getUsername());
        assertThat(user.getEmail().toString()).isEqualTo(userJoinRequest.getEmail());
        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getBio()).isEqualTo(userJoinRequest.getBio());
        assertThat(user.getImage()).isEqualTo(userJoinRequest.getImage());
    }

    @Test
    void userJoinRequestJacksonTest() throws JsonProcessingException {

        // given
        UserJoinRequest userJoinRequest = new UserJoinRequest(
            "username", "test@test.com", "password", "bio", "image"
        );
        ObjectMapper objectMapper = new ObjectMapper();

        // then
        String result = objectMapper.writeValueAsString(userJoinRequest);

        // when
        assertThat(result).isEqualTo(
            "{\"user\":{\"username\":\"" + userJoinRequest.getUsername()
                + "\",\"email\":\"" + userJoinRequest.getEmail()
                + "\",\"password\":\"" + userJoinRequest.getPassword()
                + "\",\"bio\":\"" + userJoinRequest.getBio()
                + "\",\"image\":\"" + userJoinRequest.getImage()
                + "\"}}"
        );
    }

}