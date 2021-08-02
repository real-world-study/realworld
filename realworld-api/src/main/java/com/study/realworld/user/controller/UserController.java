package com.study.realworld.user.controller;

import static org.springframework.http.ResponseEntity.ok;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.study.realworld.user.controller.dto.request.JoinRequest;
import com.study.realworld.user.controller.dto.request.LoginRequest;
import com.study.realworld.user.controller.dto.request.UpdateRequest;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.entity.User;
import com.study.realworld.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users/login")
    public ResponseEntity login(@RequestBody @Valid final LoginRequest loginRequest) {
        final User user = userService.login(loginRequest.toEntity());
        return ok(UserInfo.create(user));
    }

    @PostMapping("/users")
    public ResponseEntity join(@RequestBody @Valid final JoinRequest joinRequest) {
        final User user = userService.join(joinRequest);
        return ok(UserInfo.create(user));
    }

    @PutMapping("/user")
    public ResponseEntity update(@RequestBody @Valid final UpdateRequest updateRequest) {
        final User user = userService.update(updateRequest);
        return ok(UserInfo.create(user));
    }
}
