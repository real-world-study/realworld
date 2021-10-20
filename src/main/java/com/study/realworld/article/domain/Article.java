package com.study.realworld.article.domain;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.User;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ArticleContent articleContent;

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_article_to_user_id"))
    @ManyToOne
    private User author;

    protected Article() {
    }

    private Article(ArticleContent articleContent, User author) {
        this.articleContent = articleContent;
        this.author = author;
    }

    public static Article from(ArticleContent articleContent, User user) {
        return new Article(articleContent, user);
    }

    public Slug slug() {
        return articleContent.slug();
    }

    public Title title() {
        return articleContent.title();
    }

    public Description description() {
        return articleContent.description();
    }

    public Body body() {
        return articleContent.body();
    }

    public List<Tag> tags() {
        return articleContent.tags();
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
        return Objects.equals(articleContent, article.articleContent) && Objects
            .equals(author, article.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleContent, author);
    }

}
