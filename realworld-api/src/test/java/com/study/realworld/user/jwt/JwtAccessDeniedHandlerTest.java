package com.study.realworld.user.jwt;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
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
import org.springframework.security.access.AccessDeniedException;

@ExtendWith(MockitoExtension.class)
class JwtAccessDeniedHandlerTest {

    @InjectMocks
    private JwtAccessDeniedHandler handler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AccessDeniedException exception;

    @Test
    void handle() throws IOException {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        handler.handle(request, response, exception);
        verify(response).sendError(captor.capture());
        assertThat(captor.getValue()).isEqualTo(SC_FORBIDDEN);
    }

}
