package com.study.realworld.domain.auth.application.presentation;

import com.study.realworld.domain.auth.application.AuthLoginService;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    private final AuthLoginService authLoginServicel;

    public AuthController(final AuthLoginService authLoginServicel) {
        this.authLoginServicel = authLoginServicel;
    }

}
