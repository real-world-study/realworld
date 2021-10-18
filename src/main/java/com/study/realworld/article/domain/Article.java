package com.study.realworld.article.domain;

import com.study.realworld.user.domain.User;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
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
