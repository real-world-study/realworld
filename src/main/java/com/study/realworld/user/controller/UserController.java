package com.study.realworld.user.controller;

import static com.study.realworld.user.controller.response.UserResponse.fromUserAndToken;

import com.study.realworld.security.JwtProvider;
import com.study.realworld.user.controller.request.UserJoinRequest;
import com.study.realworld.user.controller.request.UserLoginRequest;
import com.study.realworld.user.controller.response.UserResponse;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    public UserController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.toEntity());
        String token = "token";
        return ResponseEntity.ok()
            .body(fromUserAndToken(user, token));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        String token = "token";
        return ResponseEntity.ok()
            .body(fromUserAndToken(user, token));
    }

}
