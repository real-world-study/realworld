package com.study.realworld.domain.favorite.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.favorite.domain.Favorite;
import com.study.realworld.domain.favorite.domain.FavoriteQueryRepository;
import com.study.realworld.domain.favorite.error.exception.FavoriteNotFoundException;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FavoriteQueryService {

    private final FavoriteQueryRepository favoriteQueryRepository;

    public Favorite findByUserAndArticle(final User user, final Article article) {
        return favoriteQueryRepository.findByUserAndArticle(user, article)
                .orElseThrow(() -> new FavoriteNotFoundException());
    }

    public long findByUserAndArticle(final Article article) {
        return favoriteQueryRepository.countByArticle(article);
    }
}
