package com.study.realworld.user.controller;

import com.study.realworld.user.Service.UserService;
import com.study.realworld.user.controller.request.UserJoinRequest;
import com.study.realworld.user.controller.response.UserResponse;
import com.study.realworld.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.study.realworld.user.controller.request.UserJoinRequest.*;
import static com.study.realworld.user.controller.response.UserResponse.fromUser;

@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/users")
//    public UserResponse join(@RequestBody UserJoinRequest request) {
////        User user = userService.join(from(request));
//        User user = userService.join(new User.Builder().build());
//        return  fromUser(user);
//    }
}
