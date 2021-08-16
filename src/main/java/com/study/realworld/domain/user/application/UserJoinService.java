package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserJoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserJoinService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User join(final User user) {
        final User encodedUser = user.encode(passwordEncoder);
        return userRepository.save(encodedUser);
    }

}
