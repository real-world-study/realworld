package com.study.realworld.user.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Bio {

    @Column(name = "bio")
    private String bio;

    protected Bio() {
    }

    public Bio(String bio) {
        this.bio = bio;
    }

    public String value() {
        return bio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bio bio1 = (Bio) o;
        return Objects.equals(bio, bio1.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bio);
    }

    @Override
    public String toString() {
        return bio;
    }

}
