package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.application.ArticleUserFollowQueryService;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ArticleQueryApi {

    private final ArticleUserFollowQueryService articleUserFollowQueryService;

    @GetMapping("/articles/{articleSlug}")
    public ResponseEntity<ArticleInfo> findByArticleSlug(@AuthenticationPrincipal final Long userId,
                                                         @Valid @PathVariable final ArticleSlug articleSlug) {
        final ArticleInfo articleInfo = Optional.ofNullable(userId)
                .map(it -> articleUserFollowQueryService.findByArticleSlug(it, articleSlug))
                .orElse(articleUserFollowQueryService.findByArticleSlugAndExcludeUser(articleSlug));
        return ResponseEntity.ok().body(articleInfo);
    }

    @GetMapping("/articles")
    public ResponseEntity<String> findArticles(@AuthenticationPrincipal final Long userId,
                                               final Pageable pageable,
                                               final String tag, final String author, final String favorited) {
        final String articleInfo = Optional.ofNullable(userId)
                .map(it -> articleUserFollowQueryService.findArticles(it, pageable, tag, author, favorited))
                .orElse(articleUserFollowQueryService.findArticles(pageable, tag, author, favorited));
        return ResponseEntity.ok().body(articleInfo);
    }
}
