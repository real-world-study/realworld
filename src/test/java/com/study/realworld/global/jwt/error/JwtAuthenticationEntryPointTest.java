package com.study.realworld.global.jwt.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationEntryPointTest {

    private ObjectMapper objectMapper;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);
    }

    @DisplayName("JwtAuthenticationEntryPoint 생성 테스트")
    @Test
    void constructor_test() {
        final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);

        assertAll(
                () -> assertThat(jwtAuthenticationEntryPoint).isNotNull(),
                () -> assertThat(jwtAuthenticationEntryPoint).isExactlyInstanceOf(JwtAuthenticationEntryPoint.class)
        );
    }

}