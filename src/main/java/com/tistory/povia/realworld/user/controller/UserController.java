package com.tistory.povia.realworld.user.controller;

import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/users")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest joinRequest) {
        User user = userService.join(joinRequest.toUser());

        JoinResponse joinResponse = JoinResponse.fromUser(user);
        return ResponseEntity.ok().body(joinResponse);
    }
}
