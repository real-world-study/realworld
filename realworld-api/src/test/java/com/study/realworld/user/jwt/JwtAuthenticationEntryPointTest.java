package com.study.realworld.user.jwt;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {
    @InjectMocks
    private JwtAuthenticationEntryPoint entryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException exception;

    @Test
    void commence() throws IOException {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        entryPoint.commence(request, response, exception);
        verify(response).sendError(captor.capture());
        assertThat(captor.getValue()).isEqualTo(SC_UNAUTHORIZED);
    }
}
