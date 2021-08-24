package com.tistory.povia.realworld.auth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Security에서 사용하는 인증 객체 Authentication을 그대로 사용하게 된다면 커스텀하게 내가 원하는 값을 ContextHolder에 저장할 수 없음. 따라서
 * 차후에 principal에 대해 커스텀한 값들을 넣어서 ContextHolder에 원하는 값을 사용할 수 있도록 구현
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Object principal;

    private final String credentials;

    public JwtAuthentication(Object principal, String credentials) {
        super(null);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
