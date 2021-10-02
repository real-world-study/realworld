package com.study.realworld.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.security.error.exception.UserDetailsNullPointerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

class SecurityExceptionHandlerFilterTest {

    private ObjectMapper objectMapper;
    private SecurityExceptionHandlerFilter securityExceptionHandlerFilter;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        securityExceptionHandlerFilter = new SecurityExceptionHandlerFilter(objectMapper);
    }

    @DisplayName("SecurityExceptionHandlerFilter 인스턴스 생성자 테스트")
    @Test
    void constructor_test() throws ServletException, IOException {
        final SecurityExceptionHandlerFilter securityExceptionHandlerFilter = new SecurityExceptionHandlerFilter(objectMapper);

        assertAll(
                () -> assertThat(securityExceptionHandlerFilter).isNotNull(),
                () -> assertThat(securityExceptionHandlerFilter).isExactlyInstanceOf(SecurityExceptionHandlerFilter.class)
        );
    }

    @DisplayName("SecurityExceptionHandlerFilter 인스턴스 doFilter() 예외처리 테스트")
    @Test
    void doFilter_exception_catch_test() throws ServletException, IOException {
        final SecurityExceptionHandlerFilter securityExceptionHandlerFilter = new SecurityExceptionHandlerFilter(objectMapper);
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);

        willThrow(new UserDetailsNullPointerException()).given(chain).doFilter(request, response);
        securityExceptionHandlerFilter.doFilter(request, response, chain);

        assertAll(
                () -> assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE),
                () -> assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }

    @DisplayName("SecurityExceptionHandlerFilter 인스턴스 doFilter() 테스트")
    @Test
    void doFilter_test() throws ServletException, IOException {
        final SecurityExceptionHandlerFilter securityExceptionHandlerFilter = new SecurityExceptionHandlerFilter(objectMapper);
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);

        willDoNothing().given(chain).doFilter(request, response);
        securityExceptionHandlerFilter.doFilter(request, response, chain);
    }

}