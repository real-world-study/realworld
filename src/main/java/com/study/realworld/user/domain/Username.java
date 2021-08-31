package com.study.realworld.user.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;

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

    public Username(String name) {
        checkUsername(name);

        this.name = name;
    }

    private static void checkUsername(String name) {
        checkArgument(StringUtils.isNotBlank(name), "username must be provided.");
        checkArgument(name.length() <= 20, "username length must be less then 20 characters.");
        checkArgument(checkUsernamePattern(name), "usernmae must be provided by limited pattern.");
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
