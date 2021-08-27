package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.auth.exception.JwtProviderNotSupportTokenException;
import com.study.realworld.domain.auth.exception.JwtProviderNotSupportTypeException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtProviderManager implements AuthenticationManager {

    private final JwtProvider jwtProvider;

    public JwtProviderManager(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        validateSupportable(authentication);
        validateToken((String) authentication.getDetails());
        return jwtProvider.authenticate(authentication);
    }

    private void validateSupportable(final Authentication authentication) {
        if (!jwtProvider.supports(authentication.getClass())) {
            final Class<? extends Authentication> authenticationClass = authentication.getClass();
            throw new JwtProviderNotSupportTypeException(authenticationClass.getSimpleName());
        }
    }

    private void validateToken(final String jwt) {
        if (!jwtProvider.validateToken(jwt)) {
            throw new JwtProviderNotSupportTokenException();
        }
    }

}
