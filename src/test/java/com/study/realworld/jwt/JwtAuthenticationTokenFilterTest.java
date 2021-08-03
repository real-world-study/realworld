package com.study.realworld.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationTokenFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private JwtAuthenticationTokenFilter filter;

    @BeforeEach
    void beforeEach() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("accessToken에 값이 없으면 contextholder에 값이 들어가지 않는다.")
    void blankAccessTokenTest() throws ServletException, IOException {

        // setup & given
        when(request.getHeader(any())).thenReturn("");
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNull();
    }

    @Test
    @DisplayName("accessToken이 validate되지 않으면 contextholder에 값이 들어가지 않는다.")
    void invalidTokenTest() throws ServletException, IOException {

        // setup & given
        when(request.getHeader(any())).thenReturn("token");
        when(tokenProvider.validateToken("token")).thenReturn(false);
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNull();
    }

    @Test
    @DisplayName("정상적인 accessToken이 들어왔을 때 커스텀된 Authentication이 contextholder에 들어가야 한다.")
    void successTokenTest() throws ServletException, IOException {

        // setup & given
        when(request.getHeader(any())).thenReturn("token");
        when(tokenProvider.validateToken("token")).thenReturn(true);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
            new JwtAuthentication(1L), null);
        when(tokenProvider.getAuthentication("token")).thenReturn(jwtAuthenticationToken);
        filter.doFilterInternal(request, response, filterChain);

        // when
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder
            .getContext().getAuthentication();
        JwtAuthentication principal = (JwtAuthentication) authentication.getPrincipal();

        // then
        assertThat(authentication).isNotNull();
        assertThat(principal.getId()).isEqualTo(1);
        assertThat(authentication.getCredentials()).isNull();
    }

}