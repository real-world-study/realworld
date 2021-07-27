package com.study.realworld.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Password {

    @NotBlank(message = "password must be provided.")
    @Size(min = 6, max = 20, message = "password length must be between 6 and 20 characters.")
    private String password;

    public String getPassword() {
        return password;
    }

    protected Password() {
    }

    public Password(String password) {
        this.password = password;
    }

    public static Password encode(String password, PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password));
    }

    public boolean matchPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

}
