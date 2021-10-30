package com.study.realworld.article.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.article.controller.response.ArticleResponse.ArticleResponseNested;
import com.study.realworld.article.domain.Article;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class ArticlesResponse {

    @JsonProperty("articles")
    private List<ArticleResponseNested> articleResponseNesteds;

    @JsonProperty("articlesCount")
    private int articlesCount;

    ArticlesResponse() {
    }

    private ArticlesResponse(List<ArticleResponseNested> articleResponseNesteds, int articlesCount) {
        this.articleResponseNesteds = articleResponseNesteds;
        this.articlesCount = articlesCount;
    }

    public static ArticlesResponse fromPageArticles(Page<Article> articles) {
        return new ArticlesResponse(
            articles.getContent().stream()
                .map(ArticleResponseNested::fromArticle)
                .collect(Collectors.toList()),
            articles.getContent().size()
        );
    }

}
