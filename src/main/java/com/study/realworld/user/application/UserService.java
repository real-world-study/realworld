package com.study.realworld.user.application;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.domain.user.repository.UserRepository;
import com.study.realworld.user.presentation.model.UserRegisterModel;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

/**
 * @author Jeongjoon Seo
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(UserRegisterModel userRegisterModel) {
        User user = User.builder()
                        .userName(userRegisterModel.getUserName())
                        .email(userRegisterModel.getEmail())
                        .password(userRegisterModel.getPassword())
                        .createdAt(LocalDateTime.now())
                        .build();
        return userRepository.save(user);
    }
}
