package com.study.realworld.jwt;

import static java.lang.String.valueOf;

import org.springframework.security.core.Authentication;

public class AuthenticationRequest {

    private final String principal;

    private final String credentials;

    public AuthenticationRequest(Authentication authentication) {
        this.principal = valueOf(authentication.getPrincipal());
        this.credentials = valueOf(authentication.getCredentials());
    }

    public String getPrincipal() {
        return principal;
    }

    public String getCredentials() {
        return credentials;
    }

}
