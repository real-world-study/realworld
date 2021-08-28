package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.domain.user.domain.Bio;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Image;

public class UserUpdateRequest {

    @JsonProperty("email")
    private Email email;

    @JsonProperty("bio")
    private Bio bio;

    @JsonProperty("image")
    private Image image;

    UserUpdateRequest() {
    }

    UserUpdateRequest(final Email email, final Bio bio, final Image image) {
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

}
