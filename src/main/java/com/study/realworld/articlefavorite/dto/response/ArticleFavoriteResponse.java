package com.study.realworld.articlefavorite.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.dto.response.ArticleResponse.ArticleResponseNested;
import com.study.realworld.user.domain.User;
import java.util.Objects;

public class ArticleFavoriteResponse {

    @JsonProperty("article")
    private ArticleResponseNested articleResponseNested;

    private ArticleFavoriteResponse(ArticleResponseNested articleResponseNested) {
        this.articleResponseNested = articleResponseNested;
    }

    public static ArticleFavoriteResponse from(ArticleResponseNested articleResponseNested) {
        return new ArticleFavoriteResponse(articleResponseNested);
    }

    public static ArticleFavoriteResponse fromArticleAndUser(Article article, User user) {
        return from(ArticleResponseNested.fromArticleAndUser(article, user));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleFavoriteResponse that = (ArticleFavoriteResponse) o;
        return Objects.equals(articleResponseNested, that.articleResponseNested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleResponseNested);
    }

}
