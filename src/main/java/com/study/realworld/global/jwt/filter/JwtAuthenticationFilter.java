package com.study.realworld.global.jwt.filter;

import com.study.realworld.global.common.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "bearer";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws IOException, ServletException {
        final String token = resolveToken(request);
        if ((Strings.isNotBlank(token)) && tokenProvider.validate(token)) {
            final Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(final HttpServletRequest request) {
        final String token = request.getHeader(AUTHORIZATION);
        if (Strings.isNotBlank(token)) {
            return token.replace(BEARER, EMPTY);
        }
        return EMPTY;
    }
}
