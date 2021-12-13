package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.ArticleBody;
import com.study.realworld.domain.article.domain.vo.ArticleDescription;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.domain.vo.ArticleTitle;
import com.study.realworld.domain.tag.domain.vo.TagName;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class ArticleUpdate {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonTypeName("article")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static class Request {

        @JsonProperty("title")
        private ArticleTitle articleTitle;

        @JsonProperty("description")
        private ArticleDescription articleDescription;

        @JsonProperty("body")
        private ArticleBody articleBody;

        @Builder
        public Request(final ArticleTitle articleTitle,
                       final ArticleDescription articleDescription,
                       final ArticleBody articleBody) {
            this.articleTitle = articleTitle;
            this.articleDescription = articleDescription;
            this.articleBody = articleBody;
        }

        public Optional<ArticleTitle> optionalArticleTitle() {
            return Optional.ofNullable(articleTitle);
        }

        public Optional<ArticleDescription> optionalArticleDescription() { return Optional.ofNullable(articleDescription); }

        public Optional<ArticleBody> optionalArticleBody() {
            return Optional.ofNullable(articleBody);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {

        @JsonProperty("slug")
        private ArticleSlug articleSlug;

        @JsonProperty("title")
        private ArticleTitle articleTitle;

        @JsonProperty("description")
        private ArticleDescription articleDescription;

        @JsonProperty("body")
        private ArticleBody articleBody;

        @JsonProperty("createdAt")
        private Instant createdAt;

        @JsonProperty("updatedAt")
        private Instant updatedAt;

        @JsonProperty("tagList")
        private List<TagName> tags;

        @JsonProperty("favorited")
        private boolean favorited;

        @JsonProperty("favoritesCount")
        private int favoritesCount = 0;

        @JsonProperty("author")
        private AuthorDto authorDto;

        public static Response from(final Article article) {
            final ArticleSlug articleSlug = article.articleSlug();
            final ArticleTitle articleTitle = article.articleTitle();
            final ArticleDescription articleDescription = article.articleDescription();
            final ArticleBody articleBody = article.articleBody();
            final Instant createdAt = article.createdAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
            final Instant updatedAt = article.updatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
            final AuthorDto authorDto = AuthorDto.from(article.author());

            return new Response(
                    articleSlug, articleTitle, articleDescription, articleBody,
                    createdAt, updatedAt, List.of(TagName.from("reactjs"), TagName.from("angularjs"), TagName.from("dragons")),
                    false, 0, authorDto
            );
        }

        public ArticleSlug articleSlug() {
            return articleSlug;
        }

        public ArticleTitle articleTitle() {
            return articleTitle;
        }

        public ArticleDescription articleDescription() {
            return articleDescription;
        }

        public ArticleBody articleBody() {
            return articleBody;
        }

        public List<TagName> tags() {
            return tags;
        }

        public Instant createdAt() {
            return createdAt;
        }

        public Instant updatedAt() {
            return updatedAt;
        }

        public boolean favorited() {
            return favorited;
        }

        public int favoritesCount() {
            return favoritesCount;
        }

        public AuthorDto author() {
            return authorDto;
        }

        @AllArgsConstructor(access = AccessLevel.PUBLIC)
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class AuthorDto {

            @JsonProperty("username")
            private UserName userName;

            @JsonProperty("bio")
            private UserBio userBio;

            @JsonProperty("image")
            private UserImage userImage;

            @JsonProperty("following")
            private boolean following;

            public static AuthorDto from(final User author) {
                return new AuthorDto(author.userName(), author.userBio(), author.userImage(), false);
            }

            public UserName userName() {
                return userName;
            }

            public UserBio userBio() {
                return userBio;
            }

            public UserImage userImage() {
                return userImage;
            }

            public boolean following() {
                return following;
            }
        }
    }
}
