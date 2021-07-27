package com.study.realworld.user.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jeongjoon Seo
 */
@Getter
@AllArgsConstructor
public class UserRegisterModel {

    private final String userName;
    private final String email;
    private final String password;
}
