package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.user.domain.*;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UserResponse {

    @JsonProperty("username")
    private Name username;

    @JsonProperty("email")
    private Email email;

    @JsonProperty("bio")
    private Bio bio;

    @JsonProperty("image")
    private Image image;

    @JsonProperty("token")
    private ResponseToken responseToken;

    public static final UserResponse fromUserWithToken(final User user, final ResponseToken responseToken) {
        return new UserResponse(user, responseToken);
    }

    UserResponse() {
    }

    private UserResponse(final User user, ResponseToken responseToken) {
        this.email = user.email();
        this.username = user.username();
        this.responseToken = responseToken;
        this.bio = user.bio();
        this.image = user.image();
    }

}
