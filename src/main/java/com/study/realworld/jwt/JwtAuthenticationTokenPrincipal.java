package com.study.realworld.jwt;

import com.study.realworld.user.domain.User;

public class JwtAuthenticationTokenPrincipal {

    private final Long id;

    public JwtAuthenticationTokenPrincipal(Long id) {
        this.id = id;
    }

    protected JwtAuthenticationTokenPrincipal(User user) {
        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }

}
