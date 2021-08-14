package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthLoginService {

    private final UserRepository userRepository;

    public AuthLoginService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(final Email email, final Password password) {
        final User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        // user.passwordMatches(password, new BCryptPasswordEncoder());
        return user;
    }
}
