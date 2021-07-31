package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.study.realworld.domain.user.domain.User;

@JsonRootName("user")
public final class UserJoinResponse {

    public static final UserJoinResponse fromUser(final User user) {
        return new UserJoinResponse(user);
    }

    private UserJoinResponse(final User user) {
        this.email = user.email();
        this.username = user.username();
        this.bio = user.bio();
        this.image = user.image();
    }

    @JsonProperty("email")
    private final String email;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("bio")
    private final String bio;

    @JsonProperty("image")
    private final String image;

}
