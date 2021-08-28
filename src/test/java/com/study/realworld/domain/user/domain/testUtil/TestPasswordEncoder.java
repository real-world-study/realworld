package com.study.realworld.domain.user.domain.testUtil;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class TestPasswordEncoder implements PasswordEncoder {

    private static final int LOG_ROUNDS = 15;

    @Override
    public final String encode(final CharSequence plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword.toString(), BCrypt.gensalt(LOG_ROUNDS));
    }

    @Override
    public final boolean matches(final CharSequence plainTextPassword, final String passwordInDatabase) {
        return BCrypt.checkpw(plainTextPassword.toString(), passwordInDatabase);
    }

}
