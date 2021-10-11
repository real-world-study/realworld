package com.study.realworld.domain.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
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

    @Override
    public String toString() {
        return "Bio{" +
                "bio='" + bio() + '\'' +
                '}';
    }

}
