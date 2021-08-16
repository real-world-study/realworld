package com.study.realworld.domain.auth.dto.token;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotBlank;

public final class RefreshToken {

    @NotBlank(message = "AccessToken must have not blank")
    private String refreshToken;

    RefreshToken() {
    }

    public RefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @JsonValue
    public final String refreshToken() {
        return refreshToken;
    }

}
