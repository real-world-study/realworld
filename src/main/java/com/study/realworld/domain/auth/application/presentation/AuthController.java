package com.study.realworld.domain.auth.application.presentation;

import com.study.realworld.domain.auth.application.AuthLoginService;
import com.study.realworld.domain.auth.dto.LoginRequest;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthLoginService authLoginService;

    public AuthController(final AuthLoginService authLoginService) {
        this.authLoginService = authLoginService;
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody final LoginRequest request) {
        final User user = authLoginService.login(request.email(), request.password());
        final UserResponse userResponse = UserResponse.ofUser(user);
        return ResponseEntity.ok().body(userResponse);
    }

}
