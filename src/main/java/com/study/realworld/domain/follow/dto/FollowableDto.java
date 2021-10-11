package com.study.realworld.domain.follow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.Bio;
import com.study.realworld.domain.user.domain.vo.Image;
import com.study.realworld.domain.user.domain.vo.Name;

public class FollowableDto {

    @JsonProperty("username")
    private Name username;

    @JsonProperty("bio")
    private Bio bio;

    @JsonProperty("image")
    private Image image;

    @JsonProperty("following")
    private Boolean followable;

    public static FollowableDto fromUserAndFollowable(final User me, final boolean followable) {
        return new FollowableDto(me.username(), me.bio(), me.image(), followable);
    }

    FollowableDto() {
    }

    private FollowableDto(final Name username, final Bio bio, final Image image, final Boolean followable) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.followable = followable;
    }

    @Override
    public String toString() {
        return "FollowableDto{" +
                "username=" + username +
                ", bio=" + bio +
                ", image=" + image +
                ", followable=" + followable +
                '}';
    }
}
