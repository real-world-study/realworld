package com.study.realworld.global.common;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import lombok.Builder;

public class TokenProviderDto {

    private Long userId;
    private UserEmail userEmail;
    private UserName userName;
    private UserPassword userPassword;
    private UserBio userBio;
    private UserImage userImage;

    @Builder
    public TokenProviderDto(final Long userId, final UserEmail userEmail, final UserName userName,
                            final UserPassword userPassword, final UserBio userBio, final UserImage userImage) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBio = userBio;
        this.userImage = userImage;
    }

    public static TokenProviderDto from(final User user) {
        return TokenProviderDto.builder()
                .userId(user.userId())
                .userEmail(user.userEmail())
                .userName(user.userName())
                .userPassword(user.userPassword())
                .userBio(user.userBio())
                .userImage(user.userImage())
                .build();
    }

    public Long userId() {
        return userId;
    }

    public UserEmail userEmail() {
        return userEmail;
    }

    public UserName userName() {
        return userName;
    }

    public UserPassword userPassword() {
        return userPassword;
    }

    public UserBio userBio() {
        return userBio;
    }

    public UserImage userImage() {
        return userImage;
    }
}
