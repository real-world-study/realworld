package com.study.realworld.user.controller;

import static org.springframework.http.ResponseEntity.ok;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.study.realworld.user.controller.dto.request.JoinDto;
import com.study.realworld.user.controller.dto.request.LoginDto;
import com.study.realworld.user.controller.dto.request.UpdateDto;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.entity.User;
import com.study.realworld.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation("SignIn")
    @PostMapping("/users/login")
    public ResponseEntity login(@RequestBody @Valid final LoginDto loginDto) {
        final User user = userService.login(loginDto.toEntity());
        return ok(UserInfo.create(user));
    }

    @ApiOperation("SignUp")
    @PostMapping("/users")
    public ResponseEntity join(@RequestBody @Valid final JoinDto joinDto) {
        final String encodedPassword = passwordEncoder.encode(joinDto.getPassword());
        final User user = userService.join(joinDto.toEntity(encodedPassword));
        return ok(UserInfo.create(user));
    }

    @ApiOperation("Edit UserInfo")
    @PutMapping("/user")
    public ResponseEntity update(@RequestBody @Valid final UpdateDto updateDto) {
        final User user = userService.update(updateDto.toEntity());
        return ok(UserInfo.create(user));
    }
}
