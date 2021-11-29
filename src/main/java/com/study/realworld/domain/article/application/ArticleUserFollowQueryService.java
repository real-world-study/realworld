package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import com.study.realworld.domain.follow.application.FollowQueryService;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleUserFollowQueryService {

    private final ArticleQueryService articleQueryService;
    private final UserQueryService userQueryService;
    private final FollowQueryService followQueryService;

    public ArticleInfo findByArticleSlug(final Long userId, final ArticleSlug articleSlug) {
        final Article article = articleQueryService.findByArticleSlug(articleSlug);
        final User user = userQueryService.findById(userId);
        final boolean following = followQueryService.existsByFolloweeAndFollower(article.author(), user);
        return ArticleInfo.from(article, following);
    }

    public ArticleInfo findByArticleSlugAndExcludeUser(final ArticleSlug articleSlug) {
        final Article article = articleQueryService.findByArticleSlug(articleSlug);
        return ArticleInfo.from(article, false);
    }
}
