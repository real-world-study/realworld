package com.study.realworld.article.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class Slug {

    @Column(name = "slug", length = 50, nullable = false)
    private String slug;

    protected Slug() {
    }

    private Slug(String slug) {
        this.slug = slug;
    }

    static Slug of(String slug) {
        checkSlug(slug);

        return new Slug(slug);
    }

    private static void checkSlug(String slug) {
        checkArgument(StringUtils.isNotBlank(slug), ErrorCode.INVALID_SLUG_NULL);
        checkArgument(slug.length() <= 50, ErrorCode.INVALID_SLUG_LENGTH);
    }

    public static Slug createSlugByTitle(Title title) {
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
