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
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleInfo {

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
    private List<String> tags;

    @JsonProperty("favorited")
    private boolean favorited;

    @JsonProperty("favoritesCount")
    private int favoritesCount = 0;

    @JsonProperty("author")
    private ArticleInfo.AuthorDto authorDto;

    public static ArticleInfo from(final Article article) {
        final ArticleSlug articleSlug = article.articleSlug();
        final ArticleTitle articleTitle = article.articleTitle();
        final ArticleDescription articleDescription = article.articleDescription();
        final ArticleBody articleBody = article.articleBody();
        final Instant createdAt = article.createdAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
        final Instant updatedAt = article.updatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
        final ArticleInfo.AuthorDto authorDto = ArticleInfo.AuthorDto.from(article.author());

        return new ArticleInfo(
                articleSlug, articleTitle, articleDescription, articleBody,
                createdAt, updatedAt, List.of("reactjs", "angularjs", "dragons"),
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

    public List<String> tags() {
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

    public ArticleInfo.AuthorDto author() {
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

        public static ArticleInfo.AuthorDto from(final User author) {
            return new ArticleInfo.AuthorDto(author.userName(), author.userBio(), author.userImage(), false);
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
