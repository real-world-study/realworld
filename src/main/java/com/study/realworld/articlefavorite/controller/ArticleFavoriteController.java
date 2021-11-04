package com.study.realworld.articlefavorite.controller;

import com.study.realworld.article.domain.Slug;
import com.study.realworld.articlefavorite.dto.response.ArticleFavoriteResponse;
import com.study.realworld.articlefavorite.service.ArticleFavoriteService;
import com.study.realworld.global.security.CurrentUserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ArticleFavoriteController {

    private final ArticleFavoriteService articleFavoriteService;

    public ArticleFavoriteController(ArticleFavoriteService articleFavoriteService) {
        this.articleFavoriteService = articleFavoriteService;
    }

    @PostMapping("/articles/{slug}/favorite")
    public ResponseEntity<ArticleFavoriteResponse> favoriteArticle(@PathVariable String slug, @CurrentUserId Long loginId) {
        ArticleFavoriteResponse favoriteResponse = articleFavoriteService.favoriteArticle(loginId, Slug.of(slug));
        return ResponseEntity.ok().body(favoriteResponse);
    }

    @DeleteMapping("/articles/{slug}/favorite")
    public ResponseEntity<ArticleFavoriteResponse> unfavoriteArticle(@PathVariable String slug, @CurrentUserId Long loginId) {
        ArticleFavoriteResponse favoriteResponse = articleFavoriteService.unfavoriteArticle(loginId, Slug.of(slug));
        return ResponseEntity.ok().body(favoriteResponse);
    }

}
