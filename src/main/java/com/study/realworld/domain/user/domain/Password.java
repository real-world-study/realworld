package com.study.realworld.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Password {

    @JsonValue
    @NotBlank(message = "Password must have not blank")
    private String password;

    public static Password encode(final Password password, final PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password.password));
    }

    protected Password() {
    }

    public Password(final String password) {
        this.password = password;
    }

    public boolean checkPasswordWithEncoder(final String rawPassword, final PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, password);
    }

}
