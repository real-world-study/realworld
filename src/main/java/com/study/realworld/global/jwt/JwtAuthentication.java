package com.study.realworld.global.jwt;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private JwtAuthentication(final Object principal) {
        super(null);
        this.principal = principal;
    }

    public static Authentication from(final Object principal) {
        return new JwtAuthentication(principal);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return Strings.EMPTY;
    }
}
