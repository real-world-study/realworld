package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.application.ArticleCommandService;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.dto.ArticleUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ArticleCommandApi {

    private final ArticleCommandService articleCommandService;

    @PostMapping("/articles")
    public ResponseEntity<ArticleSave.Response> save(@AuthenticationPrincipal final Long userId,
                                                     @Valid @RequestBody final ArticleSave.Request request) {
        final Article article = articleCommandService.save(userId, request);
        final ArticleSave.Response response = ArticleSave.Response.from(article);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/articles/{articleSlug}")
    public ResponseEntity<ArticleUpdate.Response> update(@AuthenticationPrincipal final Long userId,
                                                         @Valid @PathVariable final ArticleSlug articleSlug,
                                                         @Valid @RequestBody final ArticleUpdate.Request request) {
        return ResponseEntity.ok().body(articleCommandService.update(userId, articleSlug, request));
    }
}
