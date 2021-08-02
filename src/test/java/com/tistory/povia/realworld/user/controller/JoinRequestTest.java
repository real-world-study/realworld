package com.tistory.povia.realworld.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.povia.realworld.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JoinRequestTest {

    private String username;
    private String address;
    private String password;
    private String bio;
    private String image;

    @BeforeEach
    void setup() {
        username = "abcde";
        address = "test@test.com";
        password = "asdfz";
        bio = "aaaa";
        image = "njnjnj";
    }

    @Test
    @DisplayName("전체 확인")
    void fullParamTest() {
        JoinRequest joinRequest = new JoinRequest(username, address, password, bio, image);
        User user = joinRequest.toUser();

        assertAll(
                () -> assertThat(user.username()).isEqualTo(joinRequest.username()),
                () -> assertThat(user.email().address()).isEqualTo(joinRequest.address()),
                () -> assertThat(user.password()).isEqualTo(joinRequest.password()),
                () -> assertThat(user.bio()).isEqualTo(joinRequest.bio()),
                () -> assertThat(user.image()).isEqualTo(joinRequest.image()));
    }

    @Test
    @DisplayName("파싱 확인")
    void parsingTest() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JoinRequest joinRequest = new JoinRequest(username, address, password, bio, image);
        String input = objectMapper.writeValueAsString(joinRequest);

        JoinRequest newRequest = objectMapper.readValue(input, JoinRequest.class);

        assertThat(newRequest).isEqualTo(joinRequest);
    }
}
