package com.study.realworld.domain.user.domain;

import javax.validation.constraints.NotBlank;

public class Email {

    @javax.validation.constraints.Email
    @NotBlank
    private String email;

    protected Email() {
    }

    public Email(final String email) {
        this.email = email;
    }

    public String email() {
        return email;
    }
}
