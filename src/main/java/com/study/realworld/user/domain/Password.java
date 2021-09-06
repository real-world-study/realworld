package com.study.realworld.user.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.study.realworld.global.error.ErrorCode;
import com.study.realworld.global.error.exception.BusinessException;
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
        checkArgument(StringUtils.isNotBlank(password), ErrorCode.INVALID_PASSWORD_NULL.getMessage());
        checkArgument(
            password.length() >= 6 && password.length() <= 20,
            ErrorCode.INVALID_PASSWORD_LENGTH.getMessage()
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
            throw new BusinessException(ErrorCode.PASSWORD_DISMATCH);
        }
    }

}
