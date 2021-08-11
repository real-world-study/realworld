package com.study.realworld.user.application.model;

import com.study.realworld.core.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jeongjoon Seo
 */

@Getter
@AllArgsConstructor
public class UserLoginModel {

    private final String email;
    private final String password;

    public User toUser() {
        return User.builder()
                   .email(this.getEmail())
                   .password(this.getPassword())
                   .build();
    }
}
