package com.study.realworld.jwt;

import static org.springframework.util.TypeUtils.isAssignable;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public JwtAuthenticationProvider(JwtTokenProvider jwtTokenProvider,
        UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        AuthenticationRequest request = new AuthenticationRequest(authentication);
        return processAuthentication(request);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

    private Authentication processAuthentication(AuthenticationRequest request) {
        User user = userService
            .login(new Email(request.getPrincipal()), new Password(request.getCredentials()));

        JwtAuthenticationTokenPrincipal principal = new JwtAuthenticationTokenPrincipal(user);
        JwtAuthenticationToken authenticated =
            new JwtAuthenticationToken(principal, null);

        String accessToken = jwtTokenProvider.generateToken(principal);
        authenticated.setDetails(new AuthenticationResponse(accessToken, user));
        return authenticated;
    }

}
