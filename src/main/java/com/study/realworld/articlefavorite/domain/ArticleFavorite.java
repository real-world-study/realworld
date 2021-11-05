package com.study.realworld.articlefavorite.domain;

import com.study.realworld.article.domain.Article;
import com.study.realworld.user.domain.User;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favorite")
public class ArticleFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_favorite_to_user_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "article_id", nullable = false, foreignKey = @ForeignKey(name = "fk_favorite_to_article_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    protected ArticleFavorite() {
    }

    private ArticleFavorite(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleFavorite that = (ArticleFavorite) o;
        return Objects.equals(user, that.user) && Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, article);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private User user;
        private Article article;

        private Builder() {
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder article(Article article) {
            this.article = article;
            return this;
        }

        public ArticleFavorite build() {
            return new ArticleFavorite(user, article);
        }

    }

}
