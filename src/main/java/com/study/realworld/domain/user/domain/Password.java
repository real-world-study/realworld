package com.study.realworld.domain.user.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Password {

    @NotBlank
    private String password;

    protected Password() {
    }

    public Password(String password) {
        this.password = password;
    }

}
