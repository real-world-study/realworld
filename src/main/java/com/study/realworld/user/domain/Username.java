package com.study.realworld.user.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Username {

    @NotBlank(message = "username must be provided.")
    @Size(max = 20, message = "username length must be less than 20 characters.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "Invalid username name")
    private String name;

    protected Username() {
    }

    public Username(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return Objects.equals(name, username.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
