package com.study.realworld.domain.user.presentation;

import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserResponse;
import org.slf4j.Logger;
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
    public ResponseEntity<UserResponse> join(@Valid @RequestBody final UserJoinRequest userJoinRequest) {
        log.info(JOIN_REQUEST_LOG_MESSAGE, userJoinRequest.toString());
        final User user = userJoinService.join(userJoinRequest.toUser());
        final UserResponse userResponse = UserResponse.ofUser(user);
        return ResponseEntity.ok().body(userResponse);
    }

}
