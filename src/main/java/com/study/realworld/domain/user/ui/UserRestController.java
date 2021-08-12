package com.study.realworld.domain.user.ui;

import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class UserRestController {

    private static final String JOIN_REQUEST_LOG_MESSAGE = "join request: {}";

    private final Logger log = getLogger(UserRestController.class);
    private final UserJoinService userJoinService;

    public UserRestController(final UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserJoinResponse> join(@Valid @RequestBody final UserJoinRequest userJoinRequest) {
        log.info(JOIN_REQUEST_LOG_MESSAGE, userJoinRequest.toString());
        final User user = userJoinService.join(userJoinRequest.toUser());
        final UserJoinResponse userJoinResponse = UserJoinResponse.ofUser(user);
        return ResponseEntity.ok().body(userJoinResponse);
    }

}
