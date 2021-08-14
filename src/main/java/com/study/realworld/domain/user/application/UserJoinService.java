package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserJoinService {

    private final UserRepository userRepository;

    public UserJoinService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(final User user) {
        return userRepository.save(user);
    }

}
