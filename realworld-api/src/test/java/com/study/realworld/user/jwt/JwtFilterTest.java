package com.study.realworld.user.jwt;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void beforeEach() {
        SecurityContextHolder.clearContext();
    }

    @DisplayName("accessToken is null")
    @Test
    void doFilterInternal() throws ServletException, IOException {
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);
        jwtFilter.doFilterInternal(request, response, filterChain);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();
    }

    @DisplayName("Token Prefix is wrong")
    @Test
    void failAccessToken() throws ServletException, IOException {
        when(request.getHeader(AUTHORIZATION)).thenReturn("test token");
        jwtFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertThat(authentication).isNull();
    }

    @DisplayName("User does not present from parsedToken")
    @Test
    void failAuthenticate() throws ServletException, IOException {
        when(request.getHeader(AUTHORIZATION)).thenReturn("Token token");
        when(tokenProvider.validateToken(any(String.class))).thenReturn(true);
        when(tokenProvider.getUserEmail("token")).thenReturn(null);
        jwtFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();
    }

    @DisplayName("Valid User Info & successful Filter check - SecurityContextHolder")
    @Test
    void successFilter() throws ServletException, IOException {
        final String email = "DolphaGo@test.net";

        when(request.getHeader(AUTHORIZATION)).thenReturn("Token token");
        when(tokenProvider.getUserEmail("token")).thenReturn(email);
        when(tokenProvider.validateToken(any(String.class))).thenReturn(true);
        when(tokenProvider.getAuthentication(any(), any())).thenReturn(new UsernamePasswordAuthenticationToken(email, "token"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo("DolphaGo@test.net");
        assertThat(authentication.getCredentials()).isEqualTo("token");
    }
}
