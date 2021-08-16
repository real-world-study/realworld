package com.study.realworld.global.config.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProviderManager implements AuthenticationManager {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtAuthenticationProviderManager(final JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        validateSupportable(authentication);
        validateToken((String) authentication.getDetails());
        return jwtAuthenticationProvider.authenticate(authentication);
    }

    private void validateSupportable(final Authentication authentication) {
        if (jwtAuthenticationProvider.supports(authentication.getClass())) {
            throw new IllegalArgumentException();
        }
    }

    private void validateToken(final String jwt) {
        if (jwtAuthenticationProvider.validateToken(jwt)) {
            throw new IllegalArgumentException();
        }
    }

}
