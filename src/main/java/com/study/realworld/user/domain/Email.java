package com.study.realworld.user.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;

import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class Email {

    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String address;

    protected Email() {
    }

    private Email(String address) {
        this.address = address;
    }

    public static Email of(String address) {
        checkEmail(address);
        return new Email(address);
    }

    private static void checkEmail(String address) {
        checkArgument(StringUtils.isNotBlank(address), ErrorCode.INVALID_EMAIL_NULL);
        checkArgument(checkEmailPattern(address), ErrorCode.INVALID_EMAIL_PATTERN);
    }

    private static boolean checkEmailPattern(String address) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return address;
    }

}
