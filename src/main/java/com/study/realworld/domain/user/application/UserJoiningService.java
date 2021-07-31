package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.springframework.stereotype.Service;

@Service
public class UserJoiningService {

    private final UserRepository userRepository;

    public UserJoiningService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserJoinResponse join(final UserJoinRequest userJoinRequest) {
        final User entity = userRepository.save(userJoinRequest.toEntity());
        return UserJoinResponse.fromUser(entity);
    }

}
