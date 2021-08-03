package com.tistory.povia.realworld.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JoinResponseTest {
    private User user;

    @BeforeEach
    void setup() {
        user =
                User.builder()
                        .username("povia")
                        .email(new Email("povia@tistory.com"))
                        .password("povia1q2w3e4r5t")
                        .bio("흐접 개발자")
                        .image(
                                "https://archeage.xlgames.com/board/attachments/8a948b8b6172d96d01619d54dc68007a?isHtml=true&pathType=ORIGINAL")
                        .build();
    }

    @Test
    @DisplayName("전체 확인")
    void fullParamTest() {
        JoinResponse joinResponse = JoinResponse.fromUser(user);

        assertAll(
                () -> assertThat(user.username()).isEqualTo(joinResponse.username()),
                () -> assertThat(user.email().address()).isEqualTo(joinResponse.address()),
                () -> assertThat(user.password()).isEqualTo(joinResponse.password()),
                () -> assertThat(user.bio()).isEqualTo(joinResponse.bio()),
                () -> assertThat(user.image()).isEqualTo(joinResponse.image()),
                () -> assertThat(joinResponse.toString()).isNotNull(),
                () -> joinResponse.fromUser(user).equals(joinResponse),
                () -> joinResponse.hashCode());
    }

    @Test
    @DisplayName("파싱 확인")
    void parsingTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JoinResponse joinResponse = JoinResponse.fromUser(user);
        String output = objectMapper.writeValueAsString(joinResponse);

        JoinResponse targetResponse = objectMapper.readValue(output, JoinResponse.class);

        assertThat(targetResponse).isEqualTo(joinResponse);
    }
}
