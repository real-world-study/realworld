package com.study.realworld.domain.user.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestPasswordEncoder implements PasswordEncoder {

    private static final int LOG_ROUNDS = 4; // 암호 난이도 -> 속도 위해 4로 낮춤

    @Override
    public final String encode(final CharSequence plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword.toString(), BCrypt.gensalt(LOG_ROUNDS));
    }

    @Override
    public final boolean matches(final CharSequence plainTextPassword, final String passwordInDatabase) {
        return BCrypt.checkpw(plainTextPassword.toString(), passwordInDatabase);
    }

    public static PasswordEncoder initialize() {
        return new TestPasswordEncoder();
    }
}
