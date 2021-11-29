package com.study.realworld.global.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccessToken {

    @NotBlank(message = "AccessToken must have not blank")
    private String accessToken;

    @JsonValue
    public final String accessToken() {
        return accessToken;
    }

}
