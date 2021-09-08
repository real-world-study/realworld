package com.study.realworld.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.global.exception.JwtException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtExceptionFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtExceptionFilter filter;

    @Test
    @DisplayName("JwtExceptionFilter가 JwtException을 catch하면 response에 errormessage를 포함해서 반환해야 한다.")
    void catchJwtExceptionTest() throws ServletException, IOException {

        // setup & given

        doThrow(new JwtException(ErrorCode.INVALID_UNSUPPORTED_JWT)).when(filterChain).doFilter(request, response);
        String expected = "{\"errors\":{\"body\":[\"this jwt wat not supported.\"]}}";

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // when
        filter.doFilterInternal(request, response, filterChain);
        String result = stringWriter.toString();

        // then
        assertThat(result).isEqualTo(expected);
    }

}