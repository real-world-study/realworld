package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        if(!user.getPassword().equals(password)) {
            throw new RuntimeException("password miss match");
        }
        return user;
    }

}
