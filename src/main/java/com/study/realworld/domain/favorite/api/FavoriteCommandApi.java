package com.study.realworld.domain.favorite.api;

import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.favorite.application.FavoriteFacadeCommandService;
import com.study.realworld.domain.favorite.dto.FavoriteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class FavoriteCommandApi {

    private final FavoriteFacadeCommandService favoriteFacadeCommandService;

    @PostMapping("/articles/{articleSlug}/favorite")
    public ResponseEntity<FavoriteInfo> favorite(@AuthenticationPrincipal final Long userId,
                                                 @Valid @PathVariable final ArticleSlug articleSlug) {

        final FavoriteInfo favoriteInfo = favoriteFacadeCommandService.favorite(userId, articleSlug);
        return ResponseEntity.ok().body(favoriteInfo);
    }
}
