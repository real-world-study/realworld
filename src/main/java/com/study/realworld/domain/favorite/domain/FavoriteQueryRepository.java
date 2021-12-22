package com.study.realworld.domain.favorite.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.study.realworld.domain.favorite.domain.QFavorite.favorite;

@RequiredArgsConstructor
@Repository
public class FavoriteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Favorite> findByUserAndArticle(final User user, final Article article) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(favorite).where(favorite.user.eq(user), favorite.article.eq(article)).fetchOne());
    }

    public long countByArticle(final Article article) {
        return jpaQueryFactory.selectFrom(favorite).where(favorite.article.eq(article)).fetchCount();
    }
}
