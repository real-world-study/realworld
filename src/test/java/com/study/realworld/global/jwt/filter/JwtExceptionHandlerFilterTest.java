package com.study.realworld.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.jwt.error.exception.JwtAuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.testToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JwtExceptionHandlerFilterTest {

    private ObjectMapper objectMapper;
    private JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        jwtExceptionHandlerFilter = new JwtExceptionHandlerFilter(objectMapper);
    }

    @DisplayName("JwtExceptionHandlerFilter 인스턴스 생성 테스트")
    @Test
    void constructor_test() {
        final JwtExceptionHandlerFilter jwtExceptionHandlerFilter = new JwtExceptionHandlerFilter(objectMapper);

        assertAll(
                () -> assertThat(jwtExceptionHandlerFilter).isNotNull(),
                () -> assertThat(jwtExceptionHandlerFilter).isExactlyInstanceOf(JwtExceptionHandlerFilter.class)
        );
    }

    @DisplayName("JwtExceptionHandlerFilter 인스턴스 doFilter() 예외처리 테스트")
    @Test
    void doFilter_exception_catch_test() throws ServletException, IOException {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);
        request.addHeader("Authorization", testToken());

        willThrow(new JwtAuthenticationException("error")).given(chain).doFilter(request, response);
        jwtExceptionHandlerFilter.doFilter(request, response, chain);
        assertAll(
                () -> assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE),
                () -> assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }

    @DisplayName("JwtExceptionHandlerFilter 인스턴스 doFilter() 테스트")
    @Test
    void doFilter_test() throws ServletException, IOException {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);
        request.addHeader("Authorization", testToken());

        willDoNothing().given(chain).doFilter(request, response);
        jwtExceptionHandlerFilter.doFilter(request, response, chain);
    }

}