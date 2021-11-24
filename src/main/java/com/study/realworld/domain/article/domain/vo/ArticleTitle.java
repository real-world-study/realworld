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
public class ArticleTitle {

    @NotBlank(message = "")
    @Column(name = "article_title")
    private String articleTitle;

    @JsonValue
    public String articleTitle() {
        return articleTitle;
    }

    public static ArticleTitle from(final String articleTitle) {
        return new ArticleTitle(articleTitle);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleTitle that = (ArticleTitle) o;
        return Objects.equals(articleTitle(), that.articleTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleTitle());
    }
}
