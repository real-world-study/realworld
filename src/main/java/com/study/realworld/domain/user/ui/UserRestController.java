package com.study.realworld.domain.user.ui;

import com.study.realworld.domain.user.application.UserJoiningService;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class UserRestController {

    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserJoiningService userJoiningService;

    public UserRestController(final UserJoiningService userJoiningService) {
        this.userJoiningService = userJoiningService;
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserJoinResponse> join(@Valid @RequestBody final UserJoinRequest userJoinRequest) {
        log.info(userJoinRequest.toString());
        return ResponseEntity.ok().body(userJoiningService.join(userJoinRequest));
    }
}
