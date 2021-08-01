package com.study.realworld.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.study.realworld.user.controller.dto.request.JoinRequest;
import com.study.realworld.user.controller.dto.request.LoginRequest;
import com.study.realworld.user.controller.dto.request.UpdateRequest;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.entity.User;
import com.study.realworld.user.repository.UserRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo login(final LoginRequest loginRequest) {
        final User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new IllegalStateException("The user does not exist."));

        if (!hasSamePassword(user.getPassword(), loginRequest.getPassword().trim())) {
            throw new IllegalStateException("Password is wrong.");
        }

        return UserInfo.create(user);
    }

    private boolean hasSamePassword(final String password, final String rawPassword) {
        return passwordEncoder.matches(rawPassword, password);
    }

    @Transactional
    public UserInfo join(final JoinRequest joinRequest) {
        validate(joinRequest.getEmail());

        final String encodedPassword = passwordEncoder.encode(joinRequest.getPassword().trim());
        final User user = joinRequest.toEntity(encodedPassword);
        userRepository.save(user);

        return UserInfo.builder()
                       .username(user.getUsername())
                       .email(user.getEmail())
                       .build();
    }

    private void validate(final String email) {
        userRepository.findByEmail(email)
                      .ifPresent(user -> {
                          throw new IllegalStateException("This user already exists.");
                      });
    }

    @Transactional
    public UserInfo update(final UpdateRequest updateRequest) {
        final User user = userRepository.findByEmail(updateRequest.getEmail()).orElseThrow(() -> new IllegalStateException("The user does not exist."));
        user.update(updateRequest.getEmail(), updateRequest.getBio(), updateRequest.getImage());
        return UserInfo.create(user);
    }
}
