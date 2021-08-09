package com.study.realworld.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.study.realworld.bean.User;
import com.study.realworld.dao.UserDao;
import com.study.realworld.common.Errors;
import com.study.realworld.common.Func;
import com.study.realworld.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController{

    @ApiOperation(value = "사용자 등록", notes = "사용자 등록")
    @PostMapping("/users")   //post 등록 api
    public String users(@RequestBody Map<String, Object> param) throws JsonProcessingException {
        JsonObject user = new Gson().toJsonTree(param).getAsJsonObject();
        return UserService.users((JsonObject) user.get("user"));
    }
}
