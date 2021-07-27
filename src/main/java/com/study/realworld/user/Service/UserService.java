package com.study.realworld.user.Service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User join(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(param -> {
                    throw new RuntimeException("already user username");
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(param -> {
                    throw new RuntimeException("already user email");
                });
        return userRepository.save(user);
    }

}
