package com.study.realworld.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.study.realworld.exception.CustomException;
import com.study.realworld.exception.ErrorCode;
import com.study.realworld.user.controller.dto.request.JoinDto;
import com.study.realworld.user.controller.dto.request.LoginDto;
import com.study.realworld.user.controller.dto.request.UpdateDto;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.entity.User;
import com.study.realworld.user.jwt.TokenProvider;
import com.study.realworld.user.repository.UserRepository;
import com.study.realworld.util.SecurityUtil;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public UserInfo join(final JoinDto dto) {
        validateEmail(dto.getEmail());
        final User savedUser = userRepository.save(dto.toEntity().encrypt(passwordEncoder));
        final String accessToken = tokenProvider.createToken(savedUser.toAuthenticationToken());
        return UserInfo.create(savedUser, accessToken);
    }

    public UserInfo login(final LoginDto loginDto) {
        final User user = getUserByEmail(loginDto.getEmail());
        if(!user.matchPassword(passwordEncoder, loginDto.getPassword())){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        final Authentication authentication = user.toAuthenticationToken();
        final String accessToken = tokenProvider.createToken(authentication);
        return UserInfo.create(user, accessToken);
    }

    @Transactional
    public UserInfo update(final UpdateDto dto, final String currentUserEmail) {
        if(!currentUserEmail.equals(dto.getEmail())){
            validateEmail(dto.getEmail());
        }

        final User findUser = getUserByEmail(currentUserEmail);
        findUser.update(dto.getEmail(), dto.getBio(), dto.getImage());
        final String token = tokenProvider.createToken(findUser.toAuthenticationToken());
        return UserInfo.create(findUser, token);
    }

    private User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));
    }

    private void validateEmail(final String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        });
    }
}
