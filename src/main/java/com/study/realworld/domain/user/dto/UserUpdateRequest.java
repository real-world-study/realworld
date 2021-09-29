package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.Bio;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Image;
import com.study.realworld.domain.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UserUpdateRequest {

    @JsonProperty("email")
    private Email email;

    @JsonProperty("bio")
    private Bio bio;

    @JsonProperty("image")
    private Image image;

    UserUpdateRequest() {
    }

    public UserUpdateRequest(final Email email, final Bio bio, final Image image) {
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

    public final User toEntity() {
        return User.Builder()
                .email(email)
                .bio(bio)
                .image(image)
                .build();
    }

}
