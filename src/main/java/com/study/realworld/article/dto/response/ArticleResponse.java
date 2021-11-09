package com.study.realworld.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.response.ProfileResponse.ProfileResponseNested;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

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

    public static ArticleResponse fromArticleAndUser(Article article, User user) {
        return new ArticleResponse(ArticleResponseNested.fromArticleAndUser(article, user));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleResponse that = (ArticleResponse) o;
        return Objects.equals(articleResponseNested, that.articleResponseNested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleResponseNested);
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
        private List<String> tags;

        @JsonProperty("createdAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private OffsetDateTime createdAt;

        @JsonProperty("updatedAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private OffsetDateTime updatedAt;

        @JsonProperty("favorited")
        private boolean favorited;

        @JsonProperty("favoritesCount")
        private int favoritesCount;

        @JsonProperty("author")
        private ProfileResponseNested profileResponseNested;

        ArticleResponseNested() {
        }

        private ArticleResponseNested(Slug slug, Title title, Description description,
            Body body, List<String> tags, OffsetDateTime createdAt, OffsetDateTime updatedAt, boolean favorited,
            int favoritesCount, ProfileResponseNested profileResponseNested) {
            this.slug = slug;
            this.title = title;
            this.description = description;
            this.body = body;
            this.tags = tags;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.favorited = favorited;
            this.favoritesCount = favoritesCount;
            this.profileResponseNested = profileResponseNested;
        }

        public static ArticleResponseNested from(Article article, User user, boolean favorited, int favoritesCount,
            boolean following) {

            return new ArticleResponseNested(
                article.slug(),
                article.title(),
                article.description(),
                article.body(),
                article.tags(),
                article.createdAt(),
                article.updatedAt(),
                favorited,
                favoritesCount,
                ProfileResponseNested.fromProfileAndFollowing(article.author().profile(), following)
            );
        }

        public static ArticleResponseNested fromArticle(Article article) {
            return from(article, null, false, article.favoritesCount(), false);
        }

        public static ArticleResponseNested fromArticleAndUser(Article article, User user) {
            return from(article, user, user.isFavoriteArticle(article), article.favoritesCount(),
                user.isFollow(article.author()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ArticleResponseNested that = (ArticleResponseNested) o;
            return favorited == that.favorited && favoritesCount == that.favoritesCount && Objects.equals(slug, that.slug)
                && Objects.equals(title, that.title) && Objects.equals(description, that.description)
                && Objects.equals(body, that.body) && Objects.equals(tags, that.tags);
        }

    }

}
