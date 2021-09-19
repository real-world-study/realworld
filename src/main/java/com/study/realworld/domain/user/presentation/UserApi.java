package com.study.realworld.domain.user.presentation;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.application.UserUpdateService;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserResponse;
import com.study.realworld.domain.user.dto.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserApi {

    private final UserJoinService userJoinService;
    private final UserUpdateService userUpdateService;
    private final TokenProvider tokenProvider;

    public UserApi(final UserJoinService userJoinService,
                   final UserUpdateService userUpdateService,
                   final TokenProvider tokenProvider) {
        this.userJoinService = userJoinService;
        this.userUpdateService = userUpdateService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> join(@Valid @RequestBody final UserJoinRequest userJoinRequest) {
        final User user = userJoinService.join(userJoinRequest.toUser());
        final ResponseToken responseToken = tokenProvider.createToken(user);
        final UserResponse userResponse = UserResponse.fromUserWithToken(user, responseToken);
        return ResponseEntity.ok().body(userResponse);
    }


    @PutMapping("/api/users")
    public ResponseEntity<UserResponse> update(@Valid @RequestBody final UserUpdateRequest userUpdateRequest,
                                               @AuthenticationPrincipal final Long principal) {
        final User user = userUpdateService.update(userUpdateRequest.toEntity(), principal);
        final ResponseToken responseToken = tokenProvider.createToken(user);
        final UserResponse userResponse = UserResponse.fromUserWithToken(user, responseToken);
        return ResponseEntity.ok().body(userResponse);
    }

}
