package com.study.realworld.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Bio {

    @Column(name = "bio")
    private String bio;

    protected Bio() {
    }

    private Bio(String bio) {
        this.bio = bio;
    }

    public static Bio of(String bio) {
        return new Bio(bio);
    }

    @JsonValue
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

}
