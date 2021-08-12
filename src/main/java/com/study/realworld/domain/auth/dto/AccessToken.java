package com.study.realworld.domain.auth.dto;

public final class AccessToken implements AuthToken {

    private String accessToken;

    public static final AuthToken ofString(final String accessToken) {
        return new AccessToken(accessToken);
    }

    AccessToken() {
    }

    public AccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

}
