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
public class FollowResponse {

    @JsonProperty("username")
    private UserName userName;

    @JsonProperty("bio")
    private UserBio userBio;

    @JsonProperty("image")
    private UserImage userImage;

    @JsonProperty("following")
    private Boolean followable;


    public static FollowResponse from(final User user) {
        return of(user, true);
    }

    private static FollowResponse of(final User user, final boolean followable) {
        return new FollowResponse(user.userName(), user.userBio(), user.userImage(), followable);
    }

    public UserName userName() {
        return userName;
    }

    public UserBio userBio() {
        return userBio;
    }

    public UserImage userImage() {
        return userImage;
    }

    public Boolean isFollowing() {
        return followable;
    }
}
