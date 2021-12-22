package com.study.realworld.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.jwt.error.JwtErrorCode;
import com.study.realworld.global.jwt.error.JwtErrorResponse;
import com.study.realworld.global.jwt.error.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException jwtAuthenticationException) {
            final JwtErrorCode jwtErrorCode = JwtErrorCode.values(jwtAuthenticationException);
            final JwtErrorResponse jwtErrorResponse = JwtErrorResponse.from(jwtErrorCode);
            final String responseJson = objectMapper.writeValueAsString(jwtErrorResponse);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseJson);
        }
    }
}
