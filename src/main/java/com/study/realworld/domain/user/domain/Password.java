package com.study.realworld.domain.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Password {

    @NotBlank
    private String password;

    public static Password createWithEncoder(final String password, final PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password));
    }

    protected Password() {
    }

    private Password(final String password) {
        this.password = password;
    }


}
