package com.study.realworld.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Image;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
import com.study.realworld.user.service.model.UserUpdateModel;
import java.util.Optional;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UserUpdateRequest {

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

    protected UserUpdateRequest() {
    }

    public UserUpdateModel toUserUpdateModel() {
        return new UserUpdateModel(
            Optional.ofNullable(username).map(Username::of).orElse(null),
            Optional.ofNullable(email).map(Email::of).orElse(null),
            Optional.ofNullable(password).map(Password::of).orElse(null),
            Bio.of(bio),
            Image.of(image)
        );
    }

}
