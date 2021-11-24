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
public class ArticleBody {

    @NotBlank(message = "")
    @Column(name = "article_body")
    private String articleBody;

    @JsonValue
    public String articleBody() {
        return articleBody;
    }

    public static ArticleBody from(final String articleBody) {
        return new ArticleBody(articleBody);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleBody that = (ArticleBody) o;
        return Objects.equals(articleBody(), that.articleBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleBody());
    }
}
