package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.article.domain.persist.ArticleTag;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.dto.ArticleUpdate;
import com.study.realworld.domain.article.error.exception.AuthorMissMatchException;
import com.study.realworld.domain.article.strategy.SlugStrategy;
import com.study.realworld.domain.tag.application.TagCommandService;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommandService {

    private final TagCommandService tagCommandService;
    private final UserQueryService userQueryService;
    private final ArticleRepository articleRepository;
    private final SlugStrategy slugStrategy;

    public Article save(final Long userId, final ArticleSave.Request request) {
        final Article article = request.toEntity(slugStrategy);
        final User user = userQueryService.findById(userId);
        final List<ArticleTag> articleTags = request.tags().stream()
                .map(it -> tagCommandService.save(it))
                .map(it -> ArticleTag.builder().article(article).tag(it).build())
                .collect(Collectors.toList());
        article.changeAuthor(user)
                .addArticleTags(articleTags);
        return articleRepository.save(article);
    }

    public ArticleUpdate.Response update(final Long userId,
                                         final ArticleSlug articleSlug,
                                         final ArticleUpdate.Request request) {
        final User user = userQueryService.findById(userId);
        final Article article = findArticleBySlug(articleSlug);
        validateSameAuthor(article, user);
        article.changeArticleTitleAndSlug(request.optionalArticleTitle().orElse(article.articleTitle()), slugStrategy)
                .changeArticleBody(request.optionalArticleBody().orElse(article.articleBody()))
                .changeArticleDescription(request.optionalArticleDescription().orElse(article.articleDescription()));
        return ArticleUpdate.Response.from(article);
    }

    public void delete(final Long userId, final ArticleSlug articleSlug) {
        final User user = userQueryService.findById(userId);
        final Article article = findArticleBySlug(articleSlug);
        validateSameAuthor(article, user);
        article.delete();
    }

    private Article findArticleBySlug(final ArticleSlug articleSlug) {
        return articleRepository
                .findByArticleSlug(articleSlug)
                .orElseThrow(IllegalArgumentException::new);
    }

    private void validateSameAuthor(final Article article, final User user) {
        if (!article.isAuthor(user)) {
            throw new AuthorMissMatchException();
        }
    }
}
