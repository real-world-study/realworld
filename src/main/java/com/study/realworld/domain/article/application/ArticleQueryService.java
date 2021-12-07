package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleQueryRepository;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleQueryService {

    private final ArticleQueryRepository articleQueryRepository;

    public Article findByArticleSlug(final ArticleSlug articleSlug) {
        return articleQueryRepository.findByArticleSlug(articleSlug)
                .orElseThrow(IllegalArgumentException::new);
    }

    public String findArticles(final User user, final Pageable pageable, final String tag, final String author, final String favorited) {
        return articleQueryRepository.findArticles(user, pageable, tag, author, favorited);
    }

    public String findArticles(final Pageable pageable, final String tag, final String author, final String favorited) {
        return articleQueryRepository.findArticles(pageable, tag, author, favorited);
    }
}
