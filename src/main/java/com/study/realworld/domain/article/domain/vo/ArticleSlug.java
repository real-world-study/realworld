package com.study.realworld.domain.article.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ArticleSlug {

    @NotBlank(message = "")
    @Column(name = "article_slug")
    private String articleSlug;

    @JsonValue
    public String articleSlug() {
        return articleSlug;
    }

    public static ArticleSlug from(final String articleSlug) {
        return new ArticleSlug(articleSlug);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleSlug that = (ArticleSlug) o;
        return Objects.equals(articleSlug(), that.articleSlug());
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleSlug());
    }
}
