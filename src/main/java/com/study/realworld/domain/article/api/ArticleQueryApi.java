package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.application.ArticleQueryService;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ArticleQueryApi {

    private final ArticleQueryService articleQueryService;

    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleInfo> findByArticleSlug(@Valid @PathVariable("slug") final ArticleSlug articleSlug) {
        final Article article = articleQueryService.findByArticleSlug(articleSlug);
        final ArticleInfo articleInfo = ArticleInfo.from(article);
        return ResponseEntity.ok().body(articleInfo);
    }
}
