package com.study.realworld.user.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;

import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class Username {

    @Column(name = "username", length = 20, unique = true, nullable = false)
    private String name;

    protected Username() {
    }

    private Username(String name) {
        this.name = name;
    }

    public static Username of(String name) {
        checkUsername(name);
        return new Username(name);
    }

    private static void checkUsername(String name) {
        checkArgument(StringUtils.isNotBlank(name), ErrorCode.INVALID_USERNAME_NULL);
        checkArgument(name.length() <= 20, ErrorCode.INVALID_USERNAME_LENGTH);
        checkArgument(checkUsernamePattern(name), ErrorCode.INVALID_USERNAME_PATTERN);
    }

    private static boolean checkUsernamePattern(String name) {
        return matches("^[0-9a-zA-Z가-힣]*$", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Username username = (Username) o;
        return Objects.equals(name, username.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
