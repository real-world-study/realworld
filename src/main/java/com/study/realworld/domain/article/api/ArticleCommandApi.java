package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.application.ArticleCommandService;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.dto.ArticleSave;
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

    @PostMapping("/articles")
    public ResponseEntity<?> save(@AuthenticationPrincipal final Long userId,
                                  @Valid @RequestBody ArticleSave.Request request) {
        final Article article = articleCommandService.save(userId, request.toEntity());
        ArticleSave.Response response = ArticleSave.Response.from(article);
        return ResponseEntity.ok().body(response);
    }
}
