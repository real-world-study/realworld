package com.study.realworld.global.config.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.study.realworld.global.config.security.jwt.JwtAuthentication.initAuthentication;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final int BEARER_TOKEN_BEGIN_INDEX = 7;

    private final JwtProviderManager jwtProviderManager;

    public JwtFilter(final JwtProviderManager jwtProviderManager) {
        this.jwtProviderManager = jwtProviderManager;
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(final HttpServletRequest request,
                          final HttpServletResponse response,
                          final FilterChain chain) throws IOException, ServletException {
        if (isRunnableFilter(request)) {
            final Authentication authenticationResult = attemptAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        }
        chain.doFilter(request, response);
    }

    private boolean isRunnableFilter(final HttpServletRequest request) {
        final String bearerToken = mapToAuthorization(request);
        return (!isNull(bearerToken) && hasText(bearerToken) && bearerToken.startsWith(BEARER));
    }

    private Authentication attemptAuthentication(final HttpServletRequest request) {
        final String jwt = resolveToken(request);
        final Authentication authentication = initAuthentication(jwt);
        return jwtProviderManager.authenticate(authentication);
    }

    private String resolveToken(final HttpServletRequest request) {
        final String bearerToken = mapToAuthorization(request);
        return bearerToken.substring(BEARER_TOKEN_BEGIN_INDEX);
    }

    private String mapToAuthorization(final HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

}
