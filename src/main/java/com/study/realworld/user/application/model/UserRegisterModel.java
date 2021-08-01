package com.study.realworld.user.application.model;

import com.study.realworld.core.domain.user.entity.User;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jeongjoon Seo
 */
@Getter
@AllArgsConstructor
public class UserRegisterModel {

    private final String username;
    private final String email;
    private final String password;

    public User toUser() {
        return User.builder()
                   .username(this.getUsername())
                   .email(this.getEmail())
                   .password(this.getPassword())
                   .createdAt(LocalDateTime.now())
                   .build();
    }
}
