package com.study.realworld.article.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.domain.Title;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.Username;
import java.time.OffsetDateTime;
import java.util.List;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ArticleResponse {

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
    private AuthorProfile authorProfile;

    ArticleResponse() {
    }

    private ArticleResponse(Slug slug, Title title, Description description, Body body,
        List<Tag> tags, OffsetDateTime createdAt, OffsetDateTime updatedAt,
        AuthorProfile authorProfile) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorProfile = authorProfile;
    }

    public static ArticleResponse fromArticle(Article article) {
        return new ArticleResponse(
            article.slug(),
            article.title(),
            article.description(),
            article.body(),
            article.tags(),
            article.createdAt(),
            article.updatedAt(),
            new AuthorProfile(article.author().profile())
        );
    }

    public static class AuthorProfile {

        @JsonProperty("username")
        private Username username;

        @JsonProperty("bio")
        private Bio bio;

        @JsonProperty("image")
        private Image image;

        @JsonProperty("following")
        private boolean following;

        AuthorProfile() {
        }

        public AuthorProfile(Username username, Bio bio, Image image, boolean following) {
            this.username = username;
            this.bio = bio;
            this.image = image;
            this.following = following;
        }

        public AuthorProfile(Profile profile) {
            this(profile.username(), profile.bio(), profile.image(), profile.isFollow());
        }

    }

}
