package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.UserRepository;

public class AuthLoginService {

    private final UserRepository userRepository;

    public AuthLoginService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
