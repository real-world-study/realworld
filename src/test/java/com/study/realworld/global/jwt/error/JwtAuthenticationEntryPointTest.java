package com.study.realworld.global.jwt.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.jwt.error.exception.JwtAuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtAuthenticationEntryPointTest {

    private ObjectMapper objectMapper;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);
    }

    @DisplayName("JwtAuthenticationEntryPoint 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);

        assertAll(
                () -> assertThat(jwtAuthenticationEntryPoint).isNotNull(),
                () -> assertThat(jwtAuthenticationEntryPoint).isExactlyInstanceOf(JwtAuthenticationEntryPoint.class)
        );
    }

    @DisplayName("JwtAuthenticationEntryPoint 인스턴스 commence() 테스트")
    @Test
    void commence_test() throws ServletException, IOException {
        final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final JwtAuthenticationException jwtAuthenticationException = new JwtAuthenticationException("test");

        jwtAuthenticationEntryPoint.commence(request, response, jwtAuthenticationException);

        assertAll(
                () -> assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE),
                () -> assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }

}