package com.study.realworld.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.study.realworld.user.domain.User;
import java.io.IOException;
import java.util.Optional;
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
    private JwtService jwtService;

    @InjectMocks
    private JwtAuthenticationTokenFilter filter;

    @BeforeEach
    void beforeEach() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("accessToken가 Authorization 헤더값에 없으면 contextholder에 값이 들어가지 않아야 한다.")
    void failAccessTokenByAuthorizationHeaderTest() throws ServletException, IOException {

        // setup & given
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNull();
    }

    @Test
    @DisplayName("accessToken의 스키마가 일치하지 않으면 contextholder에 값이 들어가지 않아야 한다.")
    void failAccessTokenBySchemaTest() throws ServletException, IOException {

        // setup & given
        when(request.getHeader(AUTHORIZATION)).thenReturn("test token");
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNull();
    }

    @Test
    @DisplayName("accessToken의 길이가 지정된 길이가 아니면 contextholder에 값이 들어가지 않아야 한다.")
    void failAccessTokenByLegnthTest() throws ServletException, IOException {

        // setup & given
        when(request.getHeader(AUTHORIZATION)).thenReturn("Token token token");
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNull();
    }

    @Test
    @DisplayName("토큰에서 user정보를 찾을 때 없는 user id이면 contextholder에 값이 들어가지 않아야 한다.")
    void failAuthenticateByErrorUserIdTest() throws ServletException, IOException {

        // sestup & given
        when(request.getHeader(AUTHORIZATION)).thenReturn("Token token");
        when(jwtService.getUser("token")).thenReturn(Optional.empty());
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNull();
    }

    @Test
    @DisplayName("토큰에서 user정보를 정상적으로 가져왔다면 contextholder에 값이 들어가야 한다.")
    void successFilterTest() throws ServletException, IOException {

        // sestup & given
        when(request.getHeader(AUTHORIZATION)).thenReturn("Token token");
        User user = User.Builder().id(2L).build();
        when(jwtService.getUser("token")).thenReturn(Optional.ofNullable(user));
        filter.doFilterInternal(request, response, filterChain);

        // when
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // then
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(user.id());
        assertThat(authentication.getCredentials()).isEqualTo("token");
    }

}