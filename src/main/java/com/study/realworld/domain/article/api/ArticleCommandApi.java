package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.application.ArticleCommandService;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.strategy.SlugStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ArticleCommandApi {

    private final ArticleCommandService articleCommandService;
    private final SlugStrategy slugStrategy;

    @PostMapping("/articles")
    public ResponseEntity<ArticleSave.Response> save(@AuthenticationPrincipal final Long userId,
                                                     @Valid @RequestBody final ArticleSave.Request request) {
        final Article article = articleCommandService.save(userId, request.toEntity(slugStrategy));
        final ArticleSave.Response response = ArticleSave.Response.from(article);
        return ResponseEntity.ok().body(response);
    }
}
