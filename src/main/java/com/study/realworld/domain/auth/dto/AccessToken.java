package com.study.realworld.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public final class AccessToken {

    private String accessToken;

    public static final AccessToken ofString(final String accessToken) {
        return new AccessToken(accessToken);
    }

    AccessToken() {
    }

    public AccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonValue
    public String accessToken() {
        return accessToken;
    }

}
