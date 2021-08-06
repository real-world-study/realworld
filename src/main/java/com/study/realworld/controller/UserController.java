package com.study.realworld.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.study.realworld.bean.UserBean;
import com.study.realworld.common.Errors;
import com.study.realworld.common.Func;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController implements ControllerBase{
    public UserBean userBean;

    @Autowired
    public UserController(UserBean userBean) {
        this.userBean = userBean;
    }

    @ApiOperation(value = "사용자 등록", notes = "사용자 등록")
    @PostMapping("/users")   //post 등록 api
    public String users(@RequestBody Map<String, Object> param) {
        JsonObject user = new Gson().toJsonTree(param).getAsJsonObject();
        if (userBean.checkSameName(getString((JsonObject) user.get("user"), "username"))) {  //닉네임 체크
            return Func.getErrorJson(Errors.SAME_NICKNAME);
        } else if (userBean.checkSameEmail(getString((JsonObject) user.get("user"), "email"))) { //email 체크
            return Func.getErrorJson(Errors.SAME_EMAIL);
        }

        if ( userBean.registUser(getString((JsonObject) user.get("user"), "username"), getString((JsonObject) user.get("user"), "email"), getString((JsonObject) user.get("user"), "password")) < 0 ) {
            //등록하다가 에러났을경우
            return Func.getErrorJson(Errors.DB);
        }

        Map<String, Object> users = userBean.getUsers(getString((JsonObject) user.get("user"), "email"));
        if ( users == null ) {
            return Func.getErrorJson(Errors.INVALID_REQUEST);
        }
        JsonObject jsonObject = new Gson().toJsonTree(users).getAsJsonObject();
        JsonObject result = new JsonObject();
        result.add("user", jsonObject);

        return Func.getResultJson(result);
    }
}
