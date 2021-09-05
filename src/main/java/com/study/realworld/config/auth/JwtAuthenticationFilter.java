package com.study.realworld.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private static final String DOFILTERMESSAGE = "{} {} : {}";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(DOFILTERMESSAGE, request.getMethod(), request.getRequestURI(), request.getHeader(HttpHeaders.AUTHORIZATION));
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(tokenHeader != null && tokenHeader.split(" ").length == 2) {
            String tokenPrefix = tokenHeader.split(" ")[0];
            String accessToken = tokenHeader.split(" ")[1];

            if("Token".equals(tokenPrefix)) {
                String email = jwtProvider.getSubjectFromToken(accessToken);
                final Authentication authentication = new UsernamePasswordAuthenticationToken(email, accessToken, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}
