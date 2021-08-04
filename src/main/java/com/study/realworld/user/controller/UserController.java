package com.study.realworld.user.controller;

import static com.study.realworld.user.controller.request.UserJoinRequest.from;
import static com.study.realworld.user.controller.response.UserResponse.fromUserAndToken;

import com.study.realworld.jwt.JwtTokenProvider;
import com.study.realworld.user.controller.request.UserJoinRequest;
import com.study.realworld.user.controller.request.UserLoginRequest;
import com.study.realworld.user.controller.response.UserResponse;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    private final JwtTokenProvider tokenProvider;

    public UserController(UserService userService,
        JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(from(request));
        return new ResponseEntity<>(
            fromUserAndToken(user, tokenProvider.generateToken(user)), HttpStatus.OK
        );
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest request) {
        User user = userService.login(new Email(request.getEmail()), new Password(request.getPassword()));
        return new ResponseEntity<>(
            fromUserAndToken(user, tokenProvider.generateToken(user)), HttpStatus.OK
        );
    }
}
