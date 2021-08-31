package com.study.realworld.user.domain;

import static com.google.common.base.Preconditions.checkArgument;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
public class Password {

    @Column(name = "password", nullable = false)
    private String password;

    protected Password() {
    }

    public Password(String password) {
        checkPassword(password);

        this.password = password;
    }

    private static void checkPassword(String password) {
        checkArgument(StringUtils.isNotBlank(password), "password must be provided.");
        checkArgument(
            password.length() >= 6 && password.length() <= 20,
            "password length must be between 6 and 20 characters."
        );
    }

    public String password() {
        return password;
    }

    public static Password encode(Password password, PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password.password));
    }

    public void matchPassword(Password rawPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword.password, this.password)) {
            throw new RuntimeException("password is different from old password.");
        }
    }

}
