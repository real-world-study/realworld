package com.study.realworld.domain.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Bio {

    @Column(name = "bio")
    private String bio;

    protected Bio() {
    }

    public Bio(final String bio) {
        this.bio = bio;
    }

    public String bio() {
        return bio;
    }
}
