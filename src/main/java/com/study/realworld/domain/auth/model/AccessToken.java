package com.study.realworld.domain.auth.model;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotBlank;

public final class AccessToken {

    @NotBlank(message = "AccessToken must have not blank")
    private String accessToken;

    public static final AccessToken valueOfString(final String accessToken) {
        return new AccessToken(accessToken);
    }

    AccessToken() {
    }

    public AccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonValue
    public final String accessToken() {
        return accessToken;
    }

}
