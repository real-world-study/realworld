package com.study.realworld.domain.auth.application.presentation;

import com.study.realworld.domain.auth.application.AuthLoginService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthLoginService authLoginService;

    public AuthController(final AuthLoginService authLoginService) {
        this.authLoginService = authLoginService;
    }

}
