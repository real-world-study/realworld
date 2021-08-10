package com.study.realworld.service;

import com.google.gson.JsonObject;
import com.study.realworld.common.ErrorCode;
import com.study.realworld.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Object users(JsonObject user) {
        if (userDao.getUserByName(getString(user, "username")) != null) {  //닉네임 체크
            return ErrorCode.SAME_NICKNAME;
        } else if (userDao.getUserByEmail(getString(user, "email")) != null) { //email 체크
            return ErrorCode.SAME_EMAIL;
        }

        if (userDao.insertUser(getString(user, "username"),
                getString(user, "email"),
                getString(user, "password")) < 0) {
            //등록하다가 에러났을경우
            return ErrorCode.DB;
        }

        return user;
    }

    String getString(JsonObject jsonObject, String name) {
        return jsonObject.get(name).toString();
    }
}
