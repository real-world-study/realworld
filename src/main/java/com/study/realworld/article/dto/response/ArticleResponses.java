package com.study.realworld.article.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.dto.response.ArticleResponse.ArticleResponseNested;
import com.study.realworld.user.domain.User;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleResponses {

    @JsonProperty("articles")
    private List<ArticleResponseNested> articleResponseNesteds;

    @JsonProperty("articlesCount")
    private int articlesCount;

    ArticleResponses() {
    }

    private ArticleResponses(List<ArticleResponseNested> articleResponseNesteds, int articlesCount) {
        this.articleResponseNesteds = articleResponseNesteds;
        this.articlesCount = articlesCount;
    }

    public static ArticleResponses from(List<ArticleResponseNested> articleResponseNesteds, int articlesCount) {
        return new ArticleResponses(articleResponseNesteds, articlesCount);
    }

    public static ArticleResponses fromArticles(List<Article> articles) {
        return from(
            articles.stream()
                .map(ArticleResponseNested::fromArticle)
                .collect(Collectors.toList()),
            articles.size()
        );
    }

    public static ArticleResponses fromArticlesAndUser(List<Article> articles, User user) {
        return from(
            articles.stream()
                .map(article -> ArticleResponseNested.fromArticleAndUser(article, user))
                .collect(Collectors.toList()),
            articles.size()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleResponses that = (ArticleResponses) o;
        return articlesCount == that.articlesCount && Objects.equals(articleResponseNesteds, that.articleResponseNesteds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleResponseNesteds, articlesCount);
    }

}
