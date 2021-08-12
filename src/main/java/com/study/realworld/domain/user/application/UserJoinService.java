package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
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
