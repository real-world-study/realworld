package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.global.common.AccessToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.study.realworld.domain.user.domain.vo.UserPassword.encode;

public final class UserJoin {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonTypeName("user")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static final class Request {

        @JsonProperty("username")
        private UserName userName;

        @JsonProperty("email")
        private UserEmail userEmail;

        @JsonProperty("password")
        private UserPassword userPassword;

        @Builder
        public Request(final UserName userName, final UserEmail userEmail, final UserPassword userPassword) {
            this.userName = userName;
            this.userEmail = userEmail;
            this.userPassword = userPassword;
        }

        public final User toEntity(final PasswordEncoder passwordEncoder) {
            return User.builder()
                    .userName(userName)
                    .userEmail(userEmail)
                    .userPassword(encode(userPassword.userPassword(), passwordEncoder))
                    .build();
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

        public final UserEmail userEmail() {
            return userEmail;
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

        public final AccessToken accessToken() {
            return accessToken;
        }
    }
}

