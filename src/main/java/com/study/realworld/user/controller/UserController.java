package com.study.realworld.user.controller;

import com.study.realworld.user.service.UserService;
import com.study.realworld.user.controller.request.UserJoinRequest;
import com.study.realworld.user.controller.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.study.realworld.user.controller.request.UserJoinRequest.*;
import static com.study.realworld.user.controller.response.UserResponse.fromUser;

@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> join(@RequestBody UserJoinRequest request) {
        return new ResponseEntity<>(
            fromUser(userService.join(from(request))), HttpStatus.OK
        );
    }
}
