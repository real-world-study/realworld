package com.study.realworld.article.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.tag.service.TagService;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final TagService tagService;

    public ArticleService(ArticleRepository articleRepository, UserService userService, TagService tagService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.tagService = tagService;
    }

    @Transactional
    public Article createArticle(Long userId, ArticleContent articleContent) {
        User author = userService.findById(userId);

        articleContent.refreshTags(tagService.refreshTagByExistedTag(articleContent.tags()));
        Article article = Article.from(articleContent, author);
        return articleRepository.save(article);
    }

}
