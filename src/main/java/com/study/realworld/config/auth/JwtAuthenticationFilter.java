package com.study.realworld.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("{} {} : {}", request.getMethod(), request.getRequestURI(), request.getHeader(HttpHeaders.AUTHORIZATION));
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null) {
            String tokenPrefix = header.split(" ")[0];
            String accessToken = header.split(" ")[1];

            if("Token".equals(tokenPrefix) && jwtProvider.isValidToken(accessToken)) {
                String email = jwtProvider.getSubjectFromToken(accessToken);
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, accessToken, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}
