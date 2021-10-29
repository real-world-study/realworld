package com.study.realworld.article.domain;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.User;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Embeddable
public class FavoritingUsers {

    @JoinTable(name = "favorite",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> favoritingUsers = new HashSet<>();

    protected FavoritingUsers() {
    }

    private FavoritingUsers(Set<User> favoritingUsers) {
        this.favoritingUsers = favoritingUsers;
    }

    public static FavoritingUsers of(Set<User> favoritingUsers) {
        return new FavoritingUsers(favoritingUsers);
    }

    public boolean isFavorite(User user) {
        return favoritingUsers.contains(user);
    }

    public int favoritesCount() {
        return favoritingUsers.size();
    }

    public FavoritingUsers favoritingByUser(User user) {
        checkFavoritingByUser(user);

        favoritingUsers.add(user);
        return this;
    }

    private void checkFavoritingByUser(User user) {
        if (favoritingUsers.contains(user)) {
            throw new BusinessException(ErrorCode.INVALID_FAVORITE_ARTICLE);
        }
    }

    public FavoritingUsers unfavoritingByUser(User user) {
        checkUnfavoritingByUser(user);

        favoritingUsers.remove(user);
        return this;
    }

    private void checkUnfavoritingByUser(User user) {
        if (!favoritingUsers.contains(user)) {
            throw new BusinessException(ErrorCode.INVALID_UNFAVORITE_ARTICLE);
        }
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
        return Objects.equals(favoritingUsers, that.favoritingUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoritingUsers);
    }

}
