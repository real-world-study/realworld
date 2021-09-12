package com.study.realworld.security;

import com.study.realworld.global.exception.ErrorResponse;
import com.study.realworld.global.exception.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String CONTENT_TYPE = "application/json";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            log.debug("Wrong jwt request exception occurred : {}", exception.getMessage());
            sendErrorMessage(response, exception);
        }
    }

    private void sendErrorMessage(HttpServletResponse response, JwtException e) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(e.getHttpStatusValue());

        response.getWriter().write(ErrorResponse.toJson(e));
    }

}
