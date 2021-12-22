package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.domain.vo.UserName;
import com.study.realworld.global.common.AccessToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public final class UserUpdate {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonTypeName("user")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static final class Request {

        @JsonProperty("email")
        private UserEmail userEmail;

        @JsonProperty("bio")
        private UserBio userBio;

        @JsonProperty("image")
        private UserImage userImage;

        @Builder
        public Request(final UserEmail userEmail, final UserBio userBio, final UserImage userImage) {
            this.userEmail = userEmail;
            this.userBio = userBio;
            this.userImage = userImage;
        }

        public final UserEmail memberEmail() {
            return userEmail;
        }

        public final UserBio memberBio() {
            return userBio;
        }

        public final UserImage memberImage() {
            return userImage;
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonTypeName("user")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static final class Response {

        @JsonProperty("username")
        private UserName userName;

        @JsonProperty("email")
        private UserEmail userEmail;

        @JsonProperty("bio")
        private UserBio userBio;

        @JsonProperty("image")
        private UserImage userImage;

        @JsonProperty("token")
        private AccessToken accessToken;

        public static final Response fromUserWithToken(final User user, final AccessToken accessToken) {
            final UserName userName = user.userName();
            final UserEmail userEmail = user.userEmail();
            final UserBio userBio = user.userBio();
            final UserImage userImage = user.userImage();
            return new Response(userName, userEmail, userBio, userImage, accessToken);
        }

        public UserName userName() {
            return userName;
        }

        public UserEmail userEmail() {
            return userEmail;
        }

        public UserBio userBio() {
            return userBio;
        }

        public UserImage userImage() {
            return userImage;
        }
    }
}
