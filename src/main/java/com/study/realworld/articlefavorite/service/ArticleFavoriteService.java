package com.study.realworld.articlefavorite.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.articlefavorite.domain.ArticleFavoriteRepository;
import com.study.realworld.articlefavorite.dto.response.ArticleFavoriteResponse;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleFavoriteService {

    private final ArticleFavoriteRepository articleFavoriteRepository;
    private final UserService userService;
    private final ArticleService articleService;

    public ArticleFavoriteService(ArticleFavoriteRepository articleFavoriteRepository, UserService userService,
        ArticleService articleService) {
        this.articleFavoriteRepository = articleFavoriteRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    @Transactional
    public ArticleFavoriteResponse favoriteArticle(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        ArticleFavorite favorite = user.createFavoriteForFavoriting(article);
        articleFavoriteRepository.save(favorite);
        return ArticleFavoriteResponse.fromArticleAndUser(article, user);
    }

    @Transactional
    public ArticleFavoriteResponse unfavoriteArticle(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        ArticleFavorite favorite = user.createFavoriteForUnfavoriting(article);
        articleFavoriteRepository.delete(favorite);
        articleFavoriteRepository.flush();
        return ArticleFavoriteResponse.fromArticleAndUser(article, user);
    }

}
