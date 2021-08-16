package com.study.realworld.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.util.JwtTokenUtil;
import com.study.realworld.user.dto.UserLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.study.realworld.global.common.Constants.AUTHENTICATION;
import static com.study.realworld.global.common.Constants.AUTHORIZATION;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("{} : {}", request.getRequestURI(), request.getHeader(AUTHORIZATION));

        final UsernamePasswordAuthenticationToken authToken;
        try {
            final UserLoginRequest userLoginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
            authToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            logger.info("attemptAuthentication : " + authToken);
        } catch (Exception e) {
            logger.info("attemptAuthentication Exception : " + e.getMessage());
            throw new RuntimeException();
        }

//        setDetails(request, authToken);
        return super.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("successfulAuthentication : " + authResult.getPrincipal().toString());

        org.springframework.security.core.userdetails.User user = (User) authResult.getPrincipal();
        response.addHeader(AUTHENTICATION, "Bearer " + JwtTokenUtil.generateJwtToken(user));

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.info("unsuccessfulAuthentication : " + failed.getMessage());

        super.unsuccessfulAuthentication(request, response, failed);
    }

}
