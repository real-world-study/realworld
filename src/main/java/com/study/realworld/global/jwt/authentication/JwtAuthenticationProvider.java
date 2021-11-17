package com.study.realworld.global.jwt.authentication;

import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.global.security.error.exception.UserDetailsNullPointerException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserQueryService userQueryService;
    private final TokenProvider tokenProvider;

    public JwtAuthenticationProvider(final UserQueryService userQueryService, final TokenProvider tokenProvider) {
        this.userQueryService = userQueryService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String email = determineUsername(authentication);
        final User user = retrieveUser(email);
        return JwtAuthentication.ofUser(user);
    }

    private String determineUsername(final Authentication authentication) {
        final String token = (String) authentication.getDetails();
        return tokenProvider.mapToUsername(token);
    }

    private User retrieveUser(final String email) {
        final User user = userQueryService.findUserByEmail(email);
        validateUserDetailsNull(user);
        return user;
    }

    private void validateUserDetailsNull(final User user) {
        if (isNull(user)) {
            throw new UserDetailsNullPointerException();
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (JwtAuthentication.class.isAssignableFrom(authentication));
    }

}
