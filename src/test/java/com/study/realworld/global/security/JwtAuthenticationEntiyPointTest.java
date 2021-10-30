package com.study.realworld.global.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntiyPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntiyPoint;

    @Test
    @DisplayName("jwtAuthenticationEntiyPoint에서 인증에러가 나면 response에 erroremssage를 포함해서 반환해야 한다.")
    void entityPointExceptionTest() throws IOException {

        // setup & given
        String expected = "{\"errors\":{\"body\":[\"Full authentication is required to access this resource\"]}}";

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        when(authException.getMessage()).thenReturn("Full authentication is required to access this resource");

        // when
        jwtAuthenticationEntiyPoint.commence(request, response, authException);
        String result = stringWriter.toString();

        // then
        assertThat(result).isEqualTo(expected);
    }

}