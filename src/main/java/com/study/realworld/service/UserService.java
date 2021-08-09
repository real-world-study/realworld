package com.study.realworld.service;

import com.google.gson.JsonObject;
import com.study.realworld.common.Errors;
import com.study.realworld.common.Func;
import com.study.realworld.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String users(JsonObject user) {
        if (userDao.checkSameName(getString(user, "username"))) {  //닉네임 체크
            return Func.getErrorJson(Errors.SAME_NICKNAME);
        } else if (userDao.checkSameEmail(getString(user, "email"))) { //email 체크
            return Func.getErrorJson(Errors.SAME_EMAIL);
        }

        if (userDao.registUser(getString(user, "username"),
                getString(user, "email"),
                getString(user, "password")) < 0) {
            //등록하다가 에러났을경우
            return Func.getErrorJson(Errors.DB);
        }

        JsonObject result = new JsonObject();
        result.add("user", user);

        return Func.getResultJson(result);
    }

    String getString(JsonObject jsonObject, String name) {
        return jsonObject.get(name).toString();
    }
}
