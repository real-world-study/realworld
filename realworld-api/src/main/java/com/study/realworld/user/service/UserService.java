package com.study.realworld.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.study.realworld.user.entity.User;
import com.study.realworld.user.repository.UserRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(final User user) {
        final User findUser = getUserByEmail(user);
        validatePassword(findUser, user);
        return findUser;
    }

    @Transactional
    public User join(final User user) {
        validateEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public User update(final User user) {
        final User findUser = getUserByEmail(user);
        findUser.update(user.getEmail(), user.getBio(), user.getImage());
        return findUser;
    }

    private User getUserByEmail(final User user) {
        return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalStateException("The user does not exist."));
    }

    private void validateEmail(final String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalStateException("This user already exists.");
        });
    }

    private void validatePassword(final User persistentUser, final User user) {
        if (!persistentUser.hasSamePassword(passwordEncoder, user.getPassword())) {
            throw new IllegalStateException("Password is wrong.");
        }
    }

}
