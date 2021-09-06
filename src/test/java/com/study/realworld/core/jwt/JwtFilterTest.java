package com.study.realworld.core.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author JeongJoon Seo
 */
@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void beforeEach() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void successFilterTest() throws ServletException, IOException {

        // given
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Token token");
        when(tokenProvider.getUserId("token")).thenReturn(1L);
        jwtFilter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(1L);
        assertThat(authentication.getCredentials()).isEqualTo("token");
    }
}
