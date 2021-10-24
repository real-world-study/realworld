package com.study.realworld.article.controller;

import com.study.realworld.article.controller.request.ArticleCreateRequest;
import com.study.realworld.article.controller.request.ArticleUpdateRequest;
import com.study.realworld.article.controller.response.ArticleResponse;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable String slug) {
        Article article = articleService.findBySlug(Slug.of(slug));
        return ResponseEntity.ok().body(ArticleResponse.fromArticle(article));
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleCreateRequest request,
        @AuthenticationPrincipal Long loginId) {
        Article article = articleService.createArticle(loginId, request.toArticleContent());
        return ResponseEntity.ok().body(ArticleResponse.fromArticle(article));
    }

    @PutMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable String slug,
        @RequestBody ArticleUpdateRequest request, @AuthenticationPrincipal Long loginId) {
        Article article = articleService.updateArticle(loginId, Slug.of(slug), request.toArticleUpdateModel());
        return ResponseEntity.ok().body(ArticleResponse.fromArticle(article));
    }

    @DeleteMapping("/articles/{slug}")
    public void deleteArticle(@PathVariable String slug, @AuthenticationPrincipal Long loginId) {
        articleService.deleteArticleByAuthorAndSlug(loginId, Slug.of(slug));
    }

}
