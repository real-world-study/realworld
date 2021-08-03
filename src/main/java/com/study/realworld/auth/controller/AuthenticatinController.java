package com.study.realworld.auth.controller;

import com.study.realworld.auth.controller.request.UserLoginRequest;
import com.study.realworld.auth.controller.response.UserLoginResponse;
import com.study.realworld.jwt.AuthenticationResponse;
import com.study.realworld.jwt.JwtAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AuthenticatinController {

    private final AuthenticationManager authenticationManager;

    public AuthenticatinController(
        AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        AuthenticationResponse response = setContextHolder(request);
        return ResponseEntity.ok().body(
            UserLoginResponse.fromUserAndToken(response.getUser(), response.getAccessToken())
        );
    }

    private AuthenticationResponse setContextHolder(UserLoginRequest request) {
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (AuthenticationResponse) authentication.getDetails();
    }
}
