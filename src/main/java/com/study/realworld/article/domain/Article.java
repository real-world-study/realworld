package com.study.realworld.article.domain;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.User;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Slug slug;

    @Embedded
    private Title title;

    @Embedded
    private Description description;

    @Embedded
    private Body body;

    @JoinTable(name = "article_tag",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags;

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_article_to_user_id"))
    @ManyToOne
    private User author;

    protected Article() {
    }

    private Article(Slug slug, Title title, Description description, Body body, List<Tag> tags, User author) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
        this.author = author;
    }

    public Slug slug() {
        return slug;
    }

    public Title title() {
        return title;
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

    public User author() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        return Objects.equals(slug, article.slug) && Objects.equals(author, article.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, author);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private Slug slug;
        private Title title;
        private Description description;
        private Body body;
        private List<Tag> tags;
        private User author;

        private Builder() {
        }

        public Builder slug(Slug slug) {
            this.slug = slug;
            return this;
        }

        public Builder title(Title title) {
            this.title = title;
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

        public Builder author(User user) {
            this.author = user;
            return this;
        }

        public Article build() {
            return new Article(slug, title, description, body, tags, author);
        }

    }

}
