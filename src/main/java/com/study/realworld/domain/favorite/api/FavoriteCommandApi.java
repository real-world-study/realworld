package com.study.realworld.domain.favorite.api;

import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.favorite.application.FavoriteCommandService;
import com.study.realworld.domain.favorite.dto.FavoriteInfo;
import com.study.realworld.domain.favorite.dto.UnFavoriteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class FavoriteCommandApi {

    private final FavoriteCommandService favoriteCommandService;

    @PostMapping("/articles/{articleSlug}/favorite")
    public ResponseEntity<FavoriteInfo> favorite(@AuthenticationPrincipal final Long userId,
                                                 @Valid @PathVariable final ArticleSlug articleSlug) {

        final FavoriteInfo favoriteInfo = favoriteCommandService.favorite(userId, articleSlug);
        return ResponseEntity.ok().body(favoriteInfo);
    }

    @DeleteMapping("/articles/{articleSlug}/favorite")
    public ResponseEntity<UnFavoriteInfo> unFavorite(@AuthenticationPrincipal final Long userId,
                                                     @Valid @PathVariable final ArticleSlug articleSlug) {

        final UnFavoriteInfo unFavoriteInfo = favoriteCommandService.unFavorite(userId, articleSlug);
        return ResponseEntity.ok().body(unFavoriteInfo);
    }
}
