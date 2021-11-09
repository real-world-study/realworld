package com.study.realworld.article.domain;

import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.FavoritingUsers;
import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.global.domain.BaseTimeEntity;
import com.study.realworld.user.domain.User;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "article")
@Where(clause = "deleted_at is null")
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ArticleContent articleContent;

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_article_to_user_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @Embedded
    private FavoritingUsers favoritingUsers = new FavoritingUsers();

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

    public List<String> tags() {
        return articleContent.tags();
    }

    public User author() {
        return author;
    }

    public void changeTitle(Title title) {
        articleContent.changeTitle(title);
    }

    public void changeDescription(Description description) {
        articleContent.changeDescription(description);
    }

    public void changeBody(Body body) {
        articleContent.changeBody(body);
    }

    public int favoritesCount() {
        return favoritingUsers.size();
    }

    public void deleteArticle() {
        saveDeletedTime(OffsetDateTime.now());
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
