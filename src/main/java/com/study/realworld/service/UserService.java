package com.study.realworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.study.realworld.bean.User;
import com.study.realworld.common.Errors;
import com.study.realworld.common.Func;
import com.study.realworld.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String users(JsonObject user) throws JsonProcessingException {
        if (userDao.checkSameName(getString(user, "username"))) {  //닉네임 체크
            return Func.getErrorJson(Errors.SAME_NICKNAME);
        } else if (userDao.checkSameEmail(getString(user, "email"))) { //email 체크
            return Func.getErrorJson(Errors.SAME_EMAIL);
        }

        if ( userDao.registUser(getString(user, "username"),
                getString(user, "email"),
                getString(user, "password")) < 0 ) {
            //등록하다가 에러났을경우
            return Func.getErrorJson(Errors.DB);
        }

        User users = userDao.getUsers(getString(user, "email"));
        if ( users == null ) {
            return Func.getErrorJson(Errors.INVALID_REQUEST);
        }
        JsonObject result = new JsonObject();
        ObjectMapper objectMapper = new ObjectMapper();
        result.addProperty("user", objectMapper.writeValueAsString(users));

        return Func.getResultJson(result);
    }
    
    String getString(JsonObject jsonObject, String name) {
        return jsonObject.get(name).toString();
    }
}
