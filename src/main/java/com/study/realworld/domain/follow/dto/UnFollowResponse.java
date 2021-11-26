package com.study.realworld.domain.follow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName("profile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UnFollowResponse {

    @JsonProperty("username")
    private UserName userName;

    @JsonProperty("bio")
    private UserBio userBio;

    @JsonProperty("image")
    private UserImage userImage;

    @JsonProperty("following")
    private Boolean followable;

    public static final UnFollowResponse from(final User user) {
        return of(user, false);
    }

    private static final UnFollowResponse of(final User user, final boolean followable) {
        return new UnFollowResponse(user.userName(), user.userBio(), user.userImage(), followable);
    }

    public final UserName userName() {
        return userName;
    }

    public final UserBio userBio() {
        return userBio;
    }

    public final UserImage userImage() {
        return userImage;
    }

    public final Boolean isFollowing() {
        return followable;
    }
}
