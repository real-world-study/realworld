package com.study.realworld.domain.user.ui;

import com.study.realworld.domain.user.application.UserJoiningService;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserRestController {

    private final UserJoiningService userJoiningService;

    public UserRestController(final UserJoiningService userJoiningService) {
        this.userJoiningService = userJoiningService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserJoinResponse> join(final @RequestBody UserJoinRequest userJoinRequest) {
        return ResponseEntity.ok().body(userJoiningService.join(userJoinRequest));
    }
}
