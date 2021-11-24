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
public class ArticleDescription {

    @NotBlank(message = "")
    @Column(name = "article_description")
    private String articleDescription;

    @JsonValue
    public String articleDescription() {
        return articleDescription;
    }

    public static ArticleDescription from(final String articleDescription) {
        return new ArticleDescription(articleDescription);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleDescription that = (ArticleDescription) o;
        return Objects.equals(articleDescription(), that.articleDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleDescription());
    }
}
