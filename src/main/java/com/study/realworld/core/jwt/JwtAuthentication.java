package com.study.realworld.core.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author JeongJoon Seo
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Object principal;

    private final String credentials;

    public JwtAuthentication(Object principal, String credentials) {
        super(null);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
