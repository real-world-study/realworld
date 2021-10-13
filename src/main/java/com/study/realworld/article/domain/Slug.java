package com.study.realworld.article.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Slug {

    @Column(name = "slug", length = 50, nullable = false)
    private String slug;

    protected Slug() {
    }

    private Slug(String slug) {
        this.slug = slug;
    }

    public static Slug of(String slug) {
        return new Slug(slug);
    }

    public static Slug of(Title title) {
        return of(title.titleToSlug());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Slug slug1 = (Slug) o;
        return Objects.equals(slug, slug1.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }

}
