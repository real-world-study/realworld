package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleListInfo {

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
    private long favoritesCount;

    @JsonProperty("author")
    private ArticleListInfo.AuthorDto authorDto;

    public ArticleListInfo(final Article article, final boolean favorited, final long favoritesCount, final Boolean following) {
        this.articleSlug = article.articleSlug();
        this.articleTitle = article.articleTitle();
        this.articleDescription = article.articleDescription();
        this.articleBody = article.articleBody();
        this.createdAt = article.createdAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
        this.updatedAt = article.updatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
        this.tags = article.articleTags().tagNames();
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
        this.authorDto = AuthorDto.from(article.author(), following);
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

    public long favoritesCount() {
        return favoritesCount;
    }

    public ArticleListInfo.AuthorDto author() {
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

        public static ArticleListInfo.AuthorDto from(final User author, final Boolean following) {
            return new ArticleListInfo.AuthorDto(author.userName(), author.userBio(), author.userImage(), following);
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
