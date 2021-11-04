package com.study.realworld.articlefavorite.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.articlefavorite.dto.response.ArticleFavoriteResponse;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleFavoriteService {

    private final UserService userService;
    private final ArticleService articleService;

    public ArticleFavoriteService(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @Transactional
    public ArticleFavoriteResponse favoriteArticle(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        user.favoriteArticle(article);
        return ArticleFavoriteResponse.fromArticleAndUser(article, user);
    }

    @Transactional
    public ArticleFavoriteResponse unfavoriteArticle(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        user.unfavoriteArticle(article);
        return ArticleFavoriteResponse.fromArticleAndUser(article, user);
    }

}
