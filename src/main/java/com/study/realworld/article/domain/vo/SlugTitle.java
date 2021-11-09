package com.study.realworld.article.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class SlugTitle {

    @Embedded
    private Slug slug;

    @Embedded
    private Title title;

    protected SlugTitle() {
    }

    private SlugTitle(Slug slug, Title title) {
        this.slug = slug;
        this.title = title;
    }

    public static SlugTitle of(Title title) {
        return new SlugTitle(Slug.createSlugByTitle(title), title);
    }

    public Slug slug() {
        return slug;
    }

    public Title title() {
        return title;
    }

    public void changeTitle(Title title) {
        SlugTitle changeSlugTitle = SlugTitle.of(title);
        this.title = changeSlugTitle.title;
        this.slug = changeSlugTitle.slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SlugTitle slugTitle = (SlugTitle) o;
        return Objects.equals(slug, slugTitle.slug) && Objects.equals(title, slugTitle.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, title);
    }

}
