package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthLoginService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(final Email email, final Password password) {
        final User user = findByEmail(email);
        user.login(password, passwordEncoder);
        return user;
    }

    private User findByEmail(final Email email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email.email()));
    }

}
