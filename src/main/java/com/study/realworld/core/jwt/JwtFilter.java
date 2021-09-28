package com.study.realworld.core.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author JeongJoon Seo
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String BLANK = " ";
        final String TOKEN = "Token";
        final int VALID_LENGTH = 2;

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String[] headers = header.split(BLANK);
        if (headers.length == VALID_LENGTH && headers[0].equals(TOKEN) && StringUtils.hasText(headers[1])) {
            String accessToken = headers[1];
            Long userId = tokenProvider.getUserId(accessToken);
            JwtAuthentication authentication = new JwtAuthentication(userId, accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
