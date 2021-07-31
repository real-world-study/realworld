package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserJoiningService {

    private final UserRepository userRepository;

    public UserJoiningService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
