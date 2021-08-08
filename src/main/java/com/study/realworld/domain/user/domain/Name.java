package com.study.realworld.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
public class Name {

    @NotBlank(message = "Name must have not blank")
    @Column(name = "name")
    private String name;

    protected Name() { }

    public Name(final String name) {
        this.name = name;
    }

    @JsonValue
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name(), name1.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name() + '\'' +
                '}';
    }

}
