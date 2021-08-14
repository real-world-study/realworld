package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
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

    public static final UserResponse ofUser(final User user) {
        return new UserResponse(user);
    }

    UserResponse() {
    }

    private UserResponse(final User user) {
        this.email = user.email();
        this.username = user.username();
        this.bio = user.bio();
        this.image = user.image();
    }

}
