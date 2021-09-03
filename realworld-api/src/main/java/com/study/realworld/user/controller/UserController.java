package com.study.realworld.user.controller;

import static org.springframework.http.ResponseEntity.ok;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.study.realworld.user.controller.dto.request.JoinDto;
import com.study.realworld.user.controller.dto.request.LoginDto;
import com.study.realworld.user.controller.dto.request.UpdateDto;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.service.UserService;
import com.study.realworld.util.CurrentUserEmail;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation("SignUp")
    @PostMapping("/users")
    public ResponseEntity<UserInfo> join(@RequestBody @Valid final JoinDto joinDto) {
        return ok(userService.join(joinDto));
    }

    @ApiOperation("SignIn")
    @PostMapping("/users/login")
    public ResponseEntity<UserInfo> login(@RequestBody @Valid final LoginDto loginDto) {
        return ok(userService.login(loginDto));
    }

    @ApiOperation("Edit UserInfo")
    @PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> update(@RequestBody @Valid final UpdateDto updateDto, @CurrentUserEmail String currentUserEmail) {
        return ok(userService.update(updateDto, currentUserEmail));
    }
}
