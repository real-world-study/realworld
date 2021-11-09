package com.study.realworld.article.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.dto.response.ArticleResponse;
import com.study.realworld.article.dto.response.ArticleResponses;
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
    public ArticleResponse findArticleResponseBySlug(Slug slug) {
        return ArticleResponse.fromArticle(findBySlug(slug));
    }

    @Transactional(readOnly = true)
    public ArticleResponse findArticleResponseBySlug(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = findBySlug(slug);

        return ArticleResponse.fromArticleAndUser(article, user);
    }

    @Transactional(readOnly = true)
    public ArticleResponses findArticleResponsesByTagAndAuthorAndFavorited(
        Pageable pageable, String tag, String author, String favorited) {

        Page<Article> articles = findArticlesPageByTagAndAuthorAndFavorited(pageable, tag, author, favorited);
        return ArticleResponses.fromArticles(articles.getContent());
    }

    @Transactional(readOnly = true)
    public ArticleResponses findArticleResponsesByTagAndAuthorAndFavorited(
        Long userId, Pageable pageable, String tag, String author, String favorited) {

        User user = userService.findById(userId);
        Page<Article> articles = findArticlesPageByTagAndAuthorAndFavorited(pageable, tag, author, favorited);
        return ArticleResponses.fromArticlesAndUser(articles.getContent(), user);
    }

    private Page<Article> findArticlesPageByTagAndAuthorAndFavorited(
        Pageable pageable, String tag, String author, String favorited) {

        return articleRepository.findPageByTagAndAuthorAndFavorited(pageable, tag, author, favorited);
    }

    @Transactional(readOnly = true)
    public ArticleResponses findFeedArticleResponses(Long userId, Pageable pageable) {
        User user = userService.findById(userId);

        Page<Article> articles = articleRepository.findByAuthorIn(pageable, user.followees());
        return ArticleResponses.fromArticles(articles.getContent());
    }

    @Transactional
    public ArticleResponse createArticle(Long userId, ArticleContent articleContent) {
        User author = userService.findById(userId);

        articleContent.refreshTags(tagService.refreshTagByExistedTagName(articleContent.tags()));
        Article article = Article.from(articleContent, author);
        return ArticleResponse.fromArticle(articleRepository.save(article));
    }

    @Transactional
    public ArticleResponse updateArticle(Long userId, Slug slug, ArticleUpdateModel updateArticle) {
        User author = userService.findById(userId);
        Article article = findByAuthorAndSlug(author, slug);

        updateArticle.getTitle().ifPresent(article::changeTitle);
        updateArticle.getDescription().ifPresent(article::changeDescription);
        updateArticle.getBody().ifPresent(article::changeBody);

        return ArticleResponse.fromArticle(article);
    }

    @Transactional
    public void deleteArticleByAuthorAndSlug(Long userId, Slug slug) {
        User author = userService.findById(userId);
        Article article = findByAuthorAndSlug(author, slug);

        article.deleteArticle();
    }

    private Article findByAuthorAndSlug(User author, Slug slug) {
        return articleRepository.findByAuthorAndArticleContentSlugTitleSlug(author, slug)
            .orElseThrow(() -> new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_AUTHOR_AND_SLUG));
    }

}
