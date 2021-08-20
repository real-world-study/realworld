package com.study.realworld.user.application.model;

import com.study.realworld.core.domain.user.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;

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

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                   .username(this.getUsername())
                   .email(this.getEmail())
                   .password(passwordEncoder.encode(this.getPassword()))
                   .createdAt(LocalDateTime.now())
                   .build();
    }
}
