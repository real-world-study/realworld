package com.tistory.povia.realworld.auth.controller;

import com.tistory.povia.realworld.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/users/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {

        return null;
    }
}
