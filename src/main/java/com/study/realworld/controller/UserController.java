package com.study.realworld.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.realworld.common.ErrorCode;
import com.study.realworld.common.JsonFunc;
import com.study.realworld.domain.User;
import com.study.realworld.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "사용자 등록", notes = "사용자 등록")
    @PostMapping("/users")   //post 등록 api
    public String users(@JsonProperty("user") User user) throws JsonProcessingException {
        Object result = userService.users(user);
        if (result instanceof ErrorCode) {
            return JsonFunc.getErrorJson((ErrorCode) result);
        }
        return JsonFunc.getResultJson(user);
    }
}
