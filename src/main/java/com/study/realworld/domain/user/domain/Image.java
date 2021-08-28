package com.study.realworld.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Image {

    @Column(name = "image")
    private String path;

    protected Image() {
    }

    public Image(final String path) {
        this.path = path;
    }

    @JsonValue
    public String path() {
        return path;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Image image = (Image) o;
        return Objects.equals(path(), image.path());
    }

    @Override
    public int hashCode() {
        return Objects.hash(path());
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                '}';
    }

}
