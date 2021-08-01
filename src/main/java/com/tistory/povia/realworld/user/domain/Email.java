package com.tistory.povia.realworld.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(name = "email", length = 50, updatable = false, nullable = false)
    private String address;

    public Email(String email) {
        this.address = email;
    }

    public void changeEmail(String address) {
        this.address = address;
    }
}
