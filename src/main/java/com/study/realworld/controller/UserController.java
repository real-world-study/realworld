package com.study.realworld.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.study.realworld.common.ErrorCode;
import com.study.realworld.common.JsonFunc;
import com.study.realworld.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "사용자 등록", notes = "사용자 등록")
    @PostMapping("/users")   //post 등록 api
    public String users(@RequestBody Map<String, Object> param) {
        JsonObject user = new Gson().toJsonTree(param).getAsJsonObject();
        Object result = userService.users((JsonObject) user.get("user"));
        if (result instanceof ErrorCode) {
            return JsonFunc.getErrorJson((ErrorCode) result);
        }
        return JsonFunc.getResultJson(user);
    }
}
