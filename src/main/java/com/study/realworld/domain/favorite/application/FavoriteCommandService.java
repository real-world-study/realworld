package com.study.realworld.domain.favorite.application;

import com.study.realworld.domain.article.application.ArticleQueryService;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.favorite.domain.Favorite;
import com.study.realworld.domain.favorite.domain.FavoriteRepository;
import com.study.realworld.domain.favorite.dto.FavoriteInfo;
import com.study.realworld.domain.favorite.dto.UnFavoriteInfo;
import com.study.realworld.domain.follow.application.FollowQueryService;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FavoriteCommandService {

    private final UserQueryService userQueryService;
    private final ArticleQueryService articleQueryService;
    private final FollowQueryService followQueryService;
    private final FavoriteRepository favoriteRepository;

    public FavoriteInfo favorite(final Long userId, final ArticleSlug articleSlug) {
        final User user = userQueryService.findById(userId);
        final Article article = articleQueryService.findByArticleSlug(articleSlug);
        favoriteRepository.findByUserAndArticle(user, article)
                .orElseGet(() -> favoriteRepository.save(createFavorite(user, article)));
        final long favoriteCount = favoriteRepository.countByArticle(article);
        final boolean following = followQueryService.existsByFolloweeAndFollower(article.author(), user);
        return FavoriteInfo.of(article, favoriteCount, following);
    }

    public UnFavoriteInfo unFavorite(final long userId, final ArticleSlug articleSlug) {
        final User user = userQueryService.findById(userId);
        final Article article = articleQueryService.findByArticleSlug(articleSlug);
        favoriteRepository.findByUserAndArticle(user, article).ifPresent(favoriteRepository::delete);
        final long favoriteCount = favoriteRepository.countByArticle(article);
        final boolean following = followQueryService.existsByFolloweeAndFollower(article.author(), user);
        return UnFavoriteInfo.of(article, favoriteCount, following);
    }

    private Favorite createFavorite(final User user, final Article article) {
        return Favorite.builder()
                .user(user)
                .article(article)
                .build();
    }
}
