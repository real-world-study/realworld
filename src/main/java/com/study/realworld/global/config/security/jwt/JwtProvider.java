package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class JwtProvider implements AuthenticationProvider {

    private static final String USER_DETAILS_SERVICE_RETURNED_NULL_MESSAGE = "UserDetailsService returned null, which is an interface contract violation";

    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    public JwtProvider(final UserDetailsService userDetailsService,
                       final TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String email = determineUsername(authentication);
        final UserDetails userDetails = retrieveUser(email);
        return JwtAuthentication.ofUserDetails(userDetails);
    }

    private String determineUsername(final Authentication authentication) {
        final String token = (String) authentication.getDetails();
        return tokenProvider.mapToUsername(token);
    }

    private UserDetails retrieveUser(final String email) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        validateUserDetailsNull(userDetails);
        return userDetails;
    }

    private void validateUserDetailsNull(final UserDetails loadedUser) {
        if (isNull(loadedUser)) {
            throw new InternalAuthenticationServiceException(USER_DETAILS_SERVICE_RETURNED_NULL_MESSAGE);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (JwtAuthentication.class.isAssignableFrom(authentication));
    }

    public boolean validateToken(final String token) {
        return tokenProvider.validateToken(token);
    }

}
