package com.study.realworld.domain.favorite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleTag;
import com.study.realworld.domain.article.domain.vo.*;
import com.study.realworld.domain.tag.domain.persist.Tag;
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
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class FavoriteInfo {

    @JsonProperty("slug")
    private ArticleSlug articleSlug;

    @JsonProperty("title")
    private ArticleTitle articleTitle;

    @JsonProperty("description")
    private ArticleDescription articleDescription;

    @JsonProperty("body")
    private ArticleBody articleBody;

    @JsonProperty("tags")
    private Set<TagName> tagNames;

    @JsonProperty("createdAt")
    private Instant createdAt;

    @JsonProperty("updatedAt")
    private Instant updatedAt;

    @JsonProperty("favorited")
    private boolean favorited;

    @JsonProperty("favoritesCount")
    private long favoritesCount;

    @JsonProperty("author")
    private AuthorInfo authorInfo;

    public static FavoriteInfo of(final Article article, long favoritesCount, boolean following) {
        final ArticleSlug articleSlug = article.articleSlug();
        final ArticleTitle articleTitle = article.articleTitle();
        final ArticleDescription articleDescription = article.articleDescription();
        final ArticleBody articleBody = article.articleBody();
        final Set<TagName> tagNames = articleTagsToTagNames(article.articleTags());
        final Instant createdAt = article.createdAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
        final Instant updatedAt = article.updatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant();
        final AuthorInfo authorInfo = AuthorInfo.from(article.author(), following);
        return new FavoriteInfo(
                articleSlug, articleTitle, articleDescription,
                articleBody, tagNames, createdAt, updatedAt, true, favoritesCount, authorInfo);
    }

    private static Set<TagName> articleTagsToTagNames(final ArticleTags articleTags) {
        return articleTags.articleTags().stream()
                .map(ArticleTag::tag)
                .map(Tag::tagName)
                .collect(Collectors.toUnmodifiableSet());
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

    public Set<TagName> tagNames() {
        return tagNames;
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

    public AuthorInfo authorInfo() {
        return authorInfo;
    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AuthorInfo {

        @JsonProperty("username")
        private UserName userName;

        @JsonProperty("bio")
        private UserBio userBio;

        @JsonProperty("image")
        private UserImage userImage;

        @JsonProperty("following")
        private boolean following;

        public static AuthorInfo from(final User author, final boolean following) {
            return new AuthorInfo(author.userName(), author.userBio(), author.userImage(), following);
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
