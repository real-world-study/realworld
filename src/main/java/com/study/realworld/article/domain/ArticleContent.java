package com.study.realworld.article.domain;

import com.study.realworld.tag.domain.Tag;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Embeddable
public class ArticleContent {

    @Embedded
    private SlugTitle slugTitle;

    @Embedded
    private Description description;

    @Embedded
    private Body body;

    @JoinTable(name = "article_tag",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Tag> tags;

    public ArticleContent() {
    }

    private ArticleContent(SlugTitle slugTitle, Description description, Body body, List<Tag> tags) {
        this.slugTitle = slugTitle;
        this.description = description;
        this.body = body;
        this.tags = tags;
    }

    public Slug slug() {
        return slugTitle.slug();
    }

    public Title title() {
        return slugTitle.title();
    }

    public Description description() {
        return description;
    }

    public Body body() {
        return body;
    }

    public List<Tag> tags() {
        return tags;
    }

    public void changeTitle(Title title) {
        slugTitle.changeTitle(title);
    }

    public void changeDescription(Description description) {
        this.description = description;
    }

    public void changeBody(Body body) {
        this.body = body;
    }

    public ArticleContent refreshTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleContent that = (ArticleContent) o;
        return Objects.equals(slugTitle, that.slugTitle) && Objects
            .equals(description, that.description) && Objects.equals(body, that.body) && Objects
            .equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slugTitle, description, body, tags);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SlugTitle slugTitle;
        private Description description;
        private Body body;
        private List<Tag> tags;

        private Builder() {
        }

        public Builder slugTitle(SlugTitle slugTitle) {
            this.slugTitle = slugTitle;
            return this;
        }

        public Builder description(Description description) {
            this.description = description;
            return this;
        }

        public Builder body(Body body) {
            this.body = body;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public ArticleContent build() {
            return new ArticleContent(slugTitle, description, body, tags);
        }

    }

}
