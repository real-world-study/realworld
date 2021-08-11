package com.study.realworld.service;

import com.study.realworld.common.ErrorCode;
import com.study.realworld.dao.UserDao;
import com.study.realworld.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Object users(User user) {
        if (!userDao.getUserByName(user.getUsername()).isEmpty()) {  //닉네임 체크
            return ErrorCode.SAME_NICKNAME;
        } else if (!userDao.getUserByEmail(user.getEmail()).isEmpty()) { //email 체크
            return ErrorCode.SAME_EMAIL;
        }

        if (userDao.insertUser(user.getUsername(), user.getEmail(), user.getPassword()) < 0) {
            //등록하다가 에러났을경우
            return ErrorCode.DB;
        }

        return user;
    }
}
