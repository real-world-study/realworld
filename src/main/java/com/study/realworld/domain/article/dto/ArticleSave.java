package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.ArticleBody;
import com.study.realworld.domain.article.domain.vo.ArticleDescription;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.domain.vo.ArticleTitle;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleSave {

    @Builder
    public static class Request {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("body")
        private String body;

        @JsonProperty("tagList")
        private List<String> tags;

        @Builder
        public Request(final String title, final String description, final String body, final List<String> tags) {
            this.title = title;
            this.description = description;
            this.body = body;
            this.tags = tags;
        }

        public Article toEntity() {
            return null;
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
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

        @JsonProperty("tagList")
        private List<String> tags;

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
            final LocalDateTime createdAt = article.createdAt();
            final LocalDateTime updatedAt = article.updatedAt();
            final AuthorDto authorDto = AuthorDto.from(article.author());

            return new Response(
                    articleSlug, articleTitle, articleDescription, articleBody,
                    createdAt, updatedAt, List.of("dragons", "training"),
                    false, 0, authorDto
            );
        }

        public ArticleSlug slug() {
            return articleSlug;
        }

        public ArticleTitle title() {
            return articleTitle;
        }

        public ArticleDescription description() {
            return articleDescription;
        }

        public ArticleBody body() {
            return articleBody;
        }

        public List<String> tags() {
            return tags;
        }

        public LocalDateTime createdAt() {
            return createdAt;
        }

        public LocalDateTime updatedAt() {
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
            private UserName userName;
            private UserBio userBio;
            private UserImage userImage;
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
