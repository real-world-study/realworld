package com.study.realworld.domain.user.domain;

import java.util.Objects;

public class Image {

    private String path;

    protected Image() {
    }

    public Image(final String path) {
        this.path = path;
    }

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

}
