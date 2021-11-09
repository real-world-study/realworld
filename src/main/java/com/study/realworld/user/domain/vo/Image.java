package com.study.realworld.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Image {

    @Column(name = "image")
    private String url;

    protected Image() {
    }

    private Image(String url) {
        this.url = url;
    }

    public static Image of(String url) {
        return new Image(url);
    }

    @JsonValue
    public String value() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        return Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

}
