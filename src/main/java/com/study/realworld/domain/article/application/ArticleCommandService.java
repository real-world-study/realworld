package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommandService {

    private final ArticleRepository articleRepository;
    private final UserQueryService userQueryService;

    public Article save(final Long userId, final Article article) {
        final User user = userQueryService.findById(userId);
        article.changeAuthor(user);
        return articleRepository.save(article);
    }
}
