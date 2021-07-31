package com.study.realworld.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Embeddable
public class Email {

    @NotEmpty(message = "address must be provided.")
    @javax.validation.constraints.Email(message = "Invalid email address")
    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String address;

    protected Email() {
    }

    public Email(String address) {
        this.address = address;
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
