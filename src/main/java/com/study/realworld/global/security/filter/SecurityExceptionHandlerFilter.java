package com.study.realworld.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.security.error.SecurityErrorCode;
import com.study.realworld.global.security.error.SecurityErrorResponse;
import com.study.realworld.global.security.error.exception.SecurityBusinessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public SecurityExceptionHandlerFilter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityBusinessException securityBusinessException) {
            final SecurityErrorCode securityErrorCode = SecurityErrorCode.values(securityBusinessException);
            final SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse(securityErrorCode);
            final String responseJson = objectMapper.writeValueAsString(securityErrorResponse);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseJson);
        }
    }

}
