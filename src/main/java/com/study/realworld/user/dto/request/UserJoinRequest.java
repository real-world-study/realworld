package com.study.realworld.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Image;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserJoinRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("image")
    private String image;

    protected UserJoinRequest() {
    }

    public User toUser() {
        return User.Builder()
            .profile(Username.of(username), Bio.of(bio), Image.of(image))
            .email(Email.of(email))
            .password(Password.of(password))
            .build();
    }

}
