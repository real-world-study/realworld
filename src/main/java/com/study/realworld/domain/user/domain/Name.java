package com.study.realworld.domain.user.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private String name;

    protected Name() { }

    public Name(final String name) {
        this.name = name;
    }

}
