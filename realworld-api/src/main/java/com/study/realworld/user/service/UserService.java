package com.study.realworld.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.study.realworld.user.entity.User;
import com.study.realworld.user.jwt.TokenDto;
import com.study.realworld.user.jwt.TokenProvider;
import com.study.realworld.user.repository.UserRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    public User login(final User user) {
        final Authentication authentication = authenticate(user.toAuthenticationToken());
        final TokenDto token = tokenProvider.createToken(authentication);
        return getUserWithTokenByEmail(user.getEmail(), token.getAccessToken());
    }

    @Transactional
    public User join(final User user) {
        validateEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public User update(final User user) {
        final User findUser = getUserByEmail(user.getEmail());
        findUser.update(user.getEmail(), user.getBio(), user.getImage());
        return findUser;
    }

    private User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("The user does not exist."));
    }

    private void validateEmail(final String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalStateException("This user already exists.");
        });
    }

    private Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    private User getUserWithTokenByEmail(final String email, final String accessToken) {
        final User findUser = getUserByEmail(email);
        findUser.setAccessToken(accessToken);
        return findUser;
    }
}
