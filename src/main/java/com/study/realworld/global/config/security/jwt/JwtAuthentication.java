package com.study.realworld.global.config.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Object principal;
    private Object credentials;

    public static JwtAuthentication initAuthentication(final String jwt) {
        return new JwtAuthentication(jwt);
    }

    public static JwtAuthentication ofUserDetails(final UserDetails userDetails) {
        final String username = userDetails.getUsername();
        final String password = userDetails.getPassword();
        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return new JwtAuthentication(username, password, authorities);
    }

    public JwtAuthentication(final String details) {
        super(null);
        super.setDetails(details);
    }

    public JwtAuthentication(final Object principal, final Object credentials,
                             final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
