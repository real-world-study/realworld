package com.study.realworld.domain.user.util;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.domain.user.dto.UserUpdate;

public class UserFixture {

    public static final UserEmail USER_EMAIL = UserEmail.from("userEmail@email.com");
    public static final UserName USER_NAME = UserName.from("userName");
    public static final UserPassword USER_PASSWORD = UserPassword.encode("userPassword", TestPasswordEncoder.initialize());
    public static final UserBio USER_BIO = UserBio.from("userBio");
    public static final UserImage USER_IMAGE = UserImage.from("userImage");

    public static final UserEmail OTHER_USER_EMAIL = UserEmail.from("otherEmail@email.com");
    public static final UserName OTHER_USER_NAME = UserName.from("otherUserName");
    public static final UserPassword OTHER_USER_PASSWORD = UserPassword.encode("otherUserPassword", TestPasswordEncoder.initialize());
    public static final UserBio OTHER_USER_BIO = UserBio.from("otherUserBio");
    public static final UserImage OTHER_USER_IMAGE = UserImage.from("otherUserImage");

    public static final UserEmail INVALID_USER_EMAIL = UserEmail.from("invalidEmail@email.com");

    public static User createUser(final UserEmail userEmail, final UserName userName,
                                  final UserPassword userPassword, final UserBio userBio, final UserImage userImage) {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .userBio(userBio)
                .userImage(userImage)
                .build();
    }

    public static UserJoin.Request createUserJoinRequest(final UserEmail userEmail, final UserName userName, final UserPassword userPassword) {
        return UserJoin.Request.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .build();
    }

    public static UserUpdate.Request createUserUpdateRequest(final UserEmail userEmail, final UserBio userBio, final UserImage userImage) {
        return UserUpdate.Request.builder()
                .userEmail(userEmail)
                .userBio(userBio)
                .userImage(userImage)
                .build();
    }
}
