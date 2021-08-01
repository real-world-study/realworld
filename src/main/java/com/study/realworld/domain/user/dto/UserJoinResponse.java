package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UserJoinResponse {

    @NotBlank
    @JsonProperty("username")
    private String username;

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("image")
    private String image;

    public static final UserJoinResponse fromUser(final User user) {
        return new UserJoinResponse(user);
    }

    private UserJoinResponse() {
    }

    private UserJoinResponse(final User user) {
        this.email = user.email();
        this.username = user.username();
        this.bio = user.bio();
        this.image = user.image();
    }

}
