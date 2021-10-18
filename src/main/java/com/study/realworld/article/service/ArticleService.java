package com.study.realworld.article.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    public ArticleService(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    public Article createArticle(Long userId, ArticleContent articleContent) {
        User author = userService.findById(userId);
        return Article.from(articleContent, author);
    }

}
