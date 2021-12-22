package com.study.realworld.domain.favorite.util;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.favorite.domain.Favorite;
import com.study.realworld.domain.user.domain.persist.User;

public class FavoriteFixture {
    public static Favorite createFavorite(final User user, final Article article) {
        return Favorite.builder()
                .user(user)
                .article(article)
                .build();
    }
}
