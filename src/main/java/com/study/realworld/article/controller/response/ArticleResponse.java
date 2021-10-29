package com.study.realworld.article.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.domain.Title;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.controller.response.ProfileResponse.ProfileResponseNested;
import java.time.OffsetDateTime;
import java.util.List;

public class ArticleResponse {

    @JsonProperty("article")
    private ArticleResponseNested articleResponseNested;

    ArticleResponse() {
    }

    private ArticleResponse(ArticleResponseNested articleResponseNested) {
        this.articleResponseNested = articleResponseNested;
    }

    public static ArticleResponse fromArticle(Article article) {
        return new ArticleResponse(ArticleResponseNested.fromArticle(article));
    }

    public static class ArticleResponseNested {

        @JsonProperty("slug")
        private Slug slug;

        @JsonProperty("title")
        private Title title;

        @JsonProperty("description")
        private Description description;

        @JsonProperty("body")
        private Body body;

        @JsonProperty("tagList")
        private List<Tag> tags;

        @JsonProperty("createdAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private OffsetDateTime createdAt;

        @JsonProperty("updatedAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private OffsetDateTime updatedAt;

        @JsonProperty("author")
        private ProfileResponseNested profileResponseNested;

        ArticleResponseNested() {
        }

        private ArticleResponseNested(Slug slug, Title title, Description description, Body body, List<Tag> tags,
            OffsetDateTime createdAt, OffsetDateTime updatedAt, ProfileResponseNested profileResponseNested) {
            this.slug = slug;
            this.title = title;
            this.description = description;
            this.body = body;
            this.tags = tags;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.profileResponseNested = profileResponseNested;
        }

        public static ArticleResponseNested fromArticle(Article article) {
            return new ArticleResponseNested(
                article.slug(),
                article.title(),
                article.description(),
                article.body(),
                article.tags(),
                article.createdAt(),
                article.updatedAt(),
                ProfileResponseNested.ofProfile(article.author().profile())
            );
        }
    }


}
