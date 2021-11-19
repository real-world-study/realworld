package com.study.realworld.domain.user.domain.vo.util;

import com.study.realworld.domain.user.domain.vo.*;

public class UserVOFixture {
    public static final UserEmail USER_EMAIL = UserEmail.from("userEmail@email.com");
    public static final UserName USER_NAME = UserName.from("userName");
    public static final UserPassword USER_PASSWORD = UserPassword.encode("userPassword", TestPasswordEncoder.initialize());
    public static final UserBio USER_BIO = UserBio.from("userBio");
    public static final UserImage USER_IMAGE = UserImage.from("userImage");
}
