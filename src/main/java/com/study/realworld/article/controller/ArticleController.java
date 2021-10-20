package com.study.realworld.article.controller;

import com.study.realworld.article.controller.request.ArticleCreateRequest;
import com.study.realworld.article.controller.response.ArticleResponse;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleCreateRequest request,
        @AuthenticationPrincipal Long loginId) {
        Article article = articleService.createArticle(loginId, request.toArticleContent());
        return ResponseEntity.ok().body(ArticleResponse.fromArticle(article));
    }

}
