package com.study.realworld.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @JsonValue
    public String email() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email(), email1.email());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                '}';
    }

}
