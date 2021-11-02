package com.study.realworld.user.domain;

import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class ArticleFavorites {

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ArticleFavorite> favorites = new HashSet<>();

    protected ArticleFavorites() {
    }

    private ArticleFavorites(Set<ArticleFavorite> favorites) {
        this.favorites = favorites;
    }

    public static ArticleFavorites of(Set<ArticleFavorite> favorites) {
        return new ArticleFavorites(favorites);
    }

    public boolean favoriting(ArticleFavorite favorite) {
        checkIsFavorite(favorite);

        return favorites.add(favorite);
    }

    private void checkIsFavorite(ArticleFavorite favorite) {
        if (isFavoriteArticle(favorite)) {
            throw new BusinessException(ErrorCode.INVALID_FAVORITE_ARTICLE);
        }
    }

    public boolean unfavoriting(ArticleFavorite favorite) {
        checkIsUnfavorite(favorite);

        return favorites.remove(favorite);
    }

    private void checkIsUnfavorite(ArticleFavorite favorite) {
        if (!isFavoriteArticle(favorite)) {
            throw new BusinessException(ErrorCode.INVALID_UNFAVORITE_ARTICLE);
        }
    }

    public boolean isFavoriteArticle(ArticleFavorite favorite) {
        return favorites.contains(favorite);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleFavorites that = (ArticleFavorites) o;
        return Objects.equals(favorites, that.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favorites);
    }

}
