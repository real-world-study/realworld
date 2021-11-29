package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.application.ArticleUserFollowQueryService;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@RestController
public class ArticleQueryApi {

    private final ArticleUserFollowQueryService articleUserFollowQueryService;

    @GetMapping("/articles/{articleSlug}")
    public ResponseEntity<ArticleInfo> findByArticleSlug(@AuthenticationPrincipal final Long userId,
                                                         @Valid @PathVariable final ArticleSlug articleSlug) {
        if (isNull(userId)) {
            final ArticleInfo articleInfo = articleUserFollowQueryService.findByArticleSlugAndExcludeUser(articleSlug);
            return ResponseEntity.ok().body(articleInfo);
        }
        final ArticleInfo articleInfo = articleUserFollowQueryService.findByArticleSlug(userId, articleSlug);
        return ResponseEntity.ok().body(articleInfo);
    }
}
