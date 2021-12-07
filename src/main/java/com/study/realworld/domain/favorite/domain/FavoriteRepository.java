package com.study.realworld.domain.favorite.domain;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.user.domain.persist.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndArticle(final User user, final Article article);

    int countByArticle(final Article article);
}
