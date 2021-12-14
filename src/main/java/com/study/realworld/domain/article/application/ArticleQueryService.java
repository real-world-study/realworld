package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleQueryRepository;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import com.study.realworld.domain.article.dto.ArticleListInfo;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleQueryService {

    private final UserQueryService userQueryService;
    private final ArticleQueryRepository articleQueryRepository;

    public Article findArticleByArticleSlug(final ArticleSlug articleSlug) {
        return articleQueryRepository.findArticleByArticleSlug(articleSlug)
                .orElseThrow(IllegalArgumentException::new);
    }

    public ArticleInfo findByArticleSlug(final Long userId, final ArticleSlug articleSlug) {
        final User user = userQueryService.findById(userId);
        return articleQueryRepository.findByArticleSlug(user, articleSlug);
    }

    public ArticleInfo findByArticleSlug(final ArticleSlug articleSlug) {
        return articleQueryRepository.findByArticleSlug(articleSlug);
    }

    public List<ArticleListInfo> findArticles(final Long userId, final Pageable pageable, final String tag, final String author, final String favorited) {
        final User user = userQueryService.findById(userId);
        return articleQueryRepository.findArticles(user, pageable, tag, author, favorited);
    }

    public List<ArticleListInfo> findArticles(final Pageable pageable, final String tag, final String author, final String favorited) {
        return articleQueryRepository.findArticles(pageable, tag, author, favorited);
    }
}
