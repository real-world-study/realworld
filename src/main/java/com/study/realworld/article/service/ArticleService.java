package com.study.realworld.article.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.service.model.ArticleUpdateModel;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.service.TagService;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Article findBySlug(Slug slug) {
        return articleRepository.findByArticleContentSlugTitleSlug(slug)
            .orElseThrow(() -> new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));
    }

    @Transactional(readOnly = true)
    public Page<Article> findAllArticles(Pageable pageable, String tag, String author) {
        return articleRepository.findPageByTagAndAuthor(pageable, tag, author);
    }

    @Transactional
    public Article createArticle(Long userId, ArticleContent articleContent) {
        User author = userService.findById(userId);

        articleContent.refreshTags(tagService.refreshTagByExistedTag(articleContent.tags()));
        Article article = Article.from(articleContent, author);
        return articleRepository.save(article);
    }

    @Transactional
    public Article updateArticle(Long userId, Slug slug, ArticleUpdateModel updateArticle) {
        User author = userService.findById(userId);
        Article article = findByAuthorAndSlug(author, slug);

        updateArticle.getTitle().ifPresent(article::changeTitle);
        updateArticle.getDescription().ifPresent(article::changeDescription);
        updateArticle.getBody().ifPresent(article::changeBody);

        return article.updateFavoritedByUser(author);
    }

    @Transactional
    public void deleteArticleByAuthorAndSlug(Long userId, Slug slug) {
        User author = userService.findById(userId);
        Article article = findByAuthorAndSlug(author, slug);

        article.deleteArticle();
    }

    @Transactional
    public Article favoriteArticle(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = findBySlug(slug);

        return article.favoritingByUser(user);
    }

    @Transactional
    public Article unfavoriteArticle(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = findBySlug(slug);

        return article.unfavoritingByUser(user);
    }

    private Article findByAuthorAndSlug(User author, Slug slug) {
        return articleRepository.findByAuthorAndArticleContentSlugTitleSlug(author, slug)
            .orElseThrow(() -> new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_AUTHOR_AND_SLUG));
    }

}
