package com.study.realworld.jwt;

import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final String headerKey;

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationTokenFilter(JwtTokenProvider tokenProvider, String headerKey) {
        this.tokenProvider = tokenProvider;
        this.headerKey = headerKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = resolveToken(request);

        if (hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            JwtAuthentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(headerKey);
    }

}
