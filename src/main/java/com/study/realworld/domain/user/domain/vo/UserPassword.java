package com.study.realworld.domain.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.study.realworld.domain.user.error.exception.PasswordMissMatchException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserPassword {

    @NotBlank(message = "Password must have not blank")
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    public static UserPassword encode(final String rawPassword, final PasswordEncoder passwordEncoder) {
        validateNullOrBlank(rawPassword);
        return new UserPassword(passwordEncoder.encode(rawPassword));
    }

    private static void validateNullOrBlank(final String rawPassword) {
        if (rawPassword.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public void matches(final UserPassword other, final PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(other.userPassword, this.userPassword)) {
            throw new PasswordMissMatchException();
        }
    }

    @JsonValue
    public String value() {
        return userPassword;
    }

}
