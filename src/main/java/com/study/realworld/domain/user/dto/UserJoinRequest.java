package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Name;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UserJoinRequest {

    @JsonProperty("username")
    private Name username;

    @JsonProperty("email")
    private Email email;

    @JsonProperty("password")
    private Password password;

    private UserJoinRequest() {
    }

    UserJoinRequest(final Name username, final Email email, final Password password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public final User toUser() {
        return User.Builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public final String toString() {
        return "UserJoinRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
