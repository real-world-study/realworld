package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserJoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserJoinService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User join(final User requestUser) {
        final User user = requestUser.encode(passwordEncoder);
        validateDuplicatedEmail(user.email());
        return userRepository.save(user);
    }

    private void validateDuplicatedEmail(final Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(email.email());
        }
    }

}
