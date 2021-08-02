package com.study.realworld.user.domain;

import static java.lang.String.valueOf;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class Password {

    @NotBlank(message = "password must be provided.")
    @Size(min = 6, max = 20, message = "password length must be between 6 and 20 characters.")
    @Column(name = "password", nullable = false)
    private String password;

    protected Password() {
    }

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static Password encode(Password password, PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(valueOf(password)));
    }

    public boolean matchPassword(Password rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword.getPassword(), this.password);
    }

}
