package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserFindService;
import com.study.realworld.domain.user.domain.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class JwtProvider implements AuthenticationProvider {

    private static final String USER_DETAILS_SERVICE_RETURNED_NULL_MESSAGE = "UserDetailsService returned null, which is an interface contract violation";

    private final UserFindService userFindService;
    private final TokenProvider tokenProvider;

    public JwtProvider(final UserFindService userFindService, final TokenProvider tokenProvider) {
        this.userFindService = userFindService;
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
        final User user = userFindService.findUserByEmail(email);
        validateUserDetailsNull(user);
        return user;
    }

    private void validateUserDetailsNull(final User user) {
        if (isNull(user)) {
            throw new InternalAuthenticationServiceException(USER_DETAILS_SERVICE_RETURNED_NULL_MESSAGE);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (JwtAuthentication.class.isAssignableFrom(authentication));
    }

}
