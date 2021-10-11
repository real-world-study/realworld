package com.study.realworld.global.jwt.authentication;

import com.study.realworld.domain.user.domain.vo.Password;
import com.study.realworld.domain.user.domain.persist.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

import static com.study.realworld.domain.user.domain.persist.User.DEFAULT_AUTHORITY;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Long principal;
    private Password credentials;

    public static JwtAuthentication initAuthentication(final String jwt) {
        return new JwtAuthentication(jwt);
    }

    public static JwtAuthentication ofUser(final User user) {
        final Long principal = user.id();
        final Password credentials = user.password();
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(DEFAULT_AUTHORITY);
        return new JwtAuthentication(principal, credentials, Collections.singleton(authority));
    }

    private JwtAuthentication(final String details) {
        super(null);
        super.setDetails(details);
    }

    public JwtAuthentication(final Long principal, final Password credentials,
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
