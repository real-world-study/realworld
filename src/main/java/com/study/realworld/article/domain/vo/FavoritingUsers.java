package com.study.realworld.article.domain.vo;

import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class FavoritingUsers {

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private Set<ArticleFavorite> favorites = new HashSet<>();

    protected FavoritingUsers() {
    }

    private FavoritingUsers(Set<ArticleFavorite> favorites) {
        this.favorites = favorites;
    }

    public static FavoritingUsers of(Set<ArticleFavorite> favorites) {
        return new FavoritingUsers(favorites);
    }

    public int size() {
        return favorites.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavoritingUsers that = (FavoritingUsers) o;
        return Objects.equals(favorites, that.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favorites);
    }

}
