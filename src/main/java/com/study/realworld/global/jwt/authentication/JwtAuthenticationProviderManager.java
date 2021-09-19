package com.study.realworld.global.jwt.authentication;

import com.study.realworld.global.jwt.error.exception.JwtProviderNotSupportTypeException;
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
        return jwtAuthenticationProvider.authenticate(authentication);
    }

    private void validateSupportable(final Authentication authentication) {
        if (!jwtAuthenticationProvider.supports(authentication.getClass())) {
            final Class<? extends Authentication> authenticationClass = authentication.getClass();
            throw new JwtProviderNotSupportTypeException(authenticationClass.getSimpleName());
        }
    }

}
