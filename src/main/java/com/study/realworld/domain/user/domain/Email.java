package com.study.realworld.domain.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Email {

    @javax.validation.constraints.Email(message = "Invalid email address")
    @NotBlank(message = "Email must have not blank")
    @Column(name = "email")
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
