package com.study.realworld.domain.auth.presentation;

import com.study.realworld.domain.auth.application.AuthLoginService;
import com.study.realworld.domain.auth.dto.LoginRequest;
import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthApi {

    private final AuthLoginService authLoginService;
    private final TokenProvider tokenProvider;

    public AuthApi(final AuthLoginService authLoginService,
                   final TokenProvider tokenProvider) {
        this.authLoginService = authLoginService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody final LoginRequest request) {
        final User user = authLoginService.login(request.email(), request.password());
        final ResponseToken token = tokenProvider.createToken(user);
        final UserResponse userResponse = UserResponse.fromUserWithToken(user, token);
        return ResponseEntity.ok().body(userResponse);
    }

}
