package com.study.realworld.domain.article.dto;

import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.domain.vo.UserName;

public class AuthDto {

    private UserName userName;
    private UserBio userBio;
    private UserImage userImage;
    private boolean following;

    public UserName userName() {
        return userName;
    }

    public UserBio userBio() {
        return userBio;
    }

    public UserName userImage() {
        return userName;
    }

    public boolean following() {
        return following;
    }
}
