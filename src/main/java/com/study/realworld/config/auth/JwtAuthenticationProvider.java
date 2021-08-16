package com.study.realworld.config.auth;

import com.study.realworld.config.auth.jwt.JwtUserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtAuthenticationProvider(PasswordEncoder passwordEncoder, JwtUserDetailsService jwtUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        logger.info("additionalAuthenticationChecks : ");
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        logger.info("retrieveUser : ");
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String email = token.getName();
        final String password = (String) token.getCredentials();
        final JwtUserDetails jwtUserDetails = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(email);

        logger.info("authenticate : " + token);

        if(!passwordEncoder.matches(password, jwtUserDetails.getPassword())) {
            throw new BadCredentialsException(jwtUserDetails.getPassword()+"invalid password");
        }

        return new UsernamePasswordAuthenticationToken(jwtUserDetails, password, jwtUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
