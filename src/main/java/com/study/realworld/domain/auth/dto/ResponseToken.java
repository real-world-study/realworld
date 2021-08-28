package com.study.realworld.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.domain.auth.dto.token.AccessToken;
import com.study.realworld.domain.auth.dto.token.RefreshToken;

public final class ResponseToken {

    @JsonProperty("accessToken")
    private AccessToken accessToken;

    @JsonProperty("refreshToken")
    private RefreshToken refreshToken;

    ResponseToken() {
    }

    public ResponseToken(final String accessToken, final String refreshToken) {
        this(new AccessToken(accessToken), new RefreshToken(refreshToken));
    }

    public ResponseToken(final AccessToken accessToken, final RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
