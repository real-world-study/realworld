package com.study.realworld.domain.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Bio bio1 = (Bio) o;
        return Objects.equals(bio(), bio1.bio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bio());
    }

}
