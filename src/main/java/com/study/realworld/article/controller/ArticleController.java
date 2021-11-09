package com.study.realworld.article.controller;

import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.dto.request.ArticleCreateRequest;
import com.study.realworld.article.dto.request.ArticleUpdateRequest;
import com.study.realworld.article.dto.response.ArticleResponse;
import com.study.realworld.article.dto.response.ArticleResponses;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.global.security.CurrentUserId;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable String slug, @CurrentUserId Long userId) {
        ArticleResponse response = Optional.ofNullable(userId)
            .map(id -> articleService.findArticleResponseBySlug(id, Slug.of(slug)))
            .orElse(articleService.findArticleResponseBySlug(Slug.of(slug)));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles")
    public ResponseEntity<ArticleResponses> getArticles(@PageableDefault(page = 0, size = 20) Pageable pageable,
        @RequestParam(required = false) String tag, @RequestParam(required = false) String author,
        @RequestParam(required = false) String favorited,
        @CurrentUserId Long userId) {
        ArticleResponses response = Optional.ofNullable(userId)
            .map(id -> articleService.findArticleResponsesByTagAndAuthorAndFavorited(id, pageable, tag, author, favorited))
            .orElse(articleService.findArticleResponsesByTagAndAuthorAndFavorited(pageable, tag, author, favorited));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles/feed")
    public ResponseEntity<ArticleResponses> getFeedArticles(@PageableDefault(page = 0, size = 20) Pageable pageable,
        @CurrentUserId Long userId) {
        ArticleResponses response = articleService.findFeedArticleResponses(userId, pageable);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleCreateRequest request, @CurrentUserId Long loginId) {
        ArticleResponse response = articleService.createArticle(loginId, request.toArticleContent());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable String slug, @RequestBody ArticleUpdateRequest request,
        @CurrentUserId Long loginId) {
        ArticleResponse response = articleService.updateArticle(loginId, Slug.of(slug), request.toArticleUpdateModel());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/articles/{slug}")
    public void deleteArticle(@PathVariable String slug, @CurrentUserId Long loginId) {
        articleService.deleteArticleByAuthorAndSlug(loginId, Slug.of(slug));
    }

}
