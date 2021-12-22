package com.study.realworld.domain.article.util;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.vo.*;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.dto.ArticleUpdate;
import com.study.realworld.domain.tag.domain.vo.TagName;
import com.study.realworld.domain.user.domain.persist.User;

import java.util.List;

public class ArticleFixture {

    public static final ArticleSlug ARTICLE_SLUG = ArticleSlug.from("how-to-train-your-dragon");
    public static final ArticleTitle ARTICLE_TITLE = ArticleTitle.from("how to train your dragon");
    public static final ArticleBody ARTICLE_BODY = ArticleBody.from("You have to believe");
    public static final ArticleDescription ARTICLE_DESCRIPTION = ArticleDescription.from("Ever wonder how?");

    public static final ArticleSlug OTHER_ARTICLE_SLUG = ArticleSlug.from("other-how-to-train-your-dragon");
    public static final ArticleTitle OTHER_ARTICLE_TITLE = ArticleTitle.from("other how to train your dragon");
    public static final ArticleBody OTHER_ARTICLE_BODY = ArticleBody.from("other You have to believe");
    public static final ArticleDescription OTHER_ARTICLE_DESCRIPTION = ArticleDescription.from("other Ever wonder how?");

    public static Article createArticle(final ArticleSlug articleSlug, final ArticleTitle articleTitle,
                                        final ArticleBody articleBody, final ArticleDescription articleDescription,
                                        final User author) {
        return Article.builder()
                .articleSlug(articleSlug)
                .articleTitle(articleTitle)
                .articleBody(articleBody)
                .articleDescription(articleDescription)
                .author(author)
                .build();
    }

    public static ArticleSave.Request createArticleSaveRequest(final ArticleTitle articleTitle,
                                                               final ArticleBody articleBody,
                                                               final ArticleDescription articleDescription) {
        return ArticleSave.Request.builder()
                .articleTitle(articleTitle)
                .articleBody(articleBody)
                .articleDescription(articleDescription)
                .tags(List.of(TagName.from("reactjs"), TagName.from("angularjs"), TagName.from("dragons")))
                .build();
    }

    public static ArticleUpdate.Request createArticleUpdateRequest(final ArticleTitle articleTitle,
                                                                   final ArticleBody articleBody,
                                                                   final ArticleDescription articleDescription) {
        return ArticleUpdate.Request.builder()
                .articleTitle(articleTitle)
                .articleBody(articleBody)
                .articleDescription(articleDescription)
                .build();
    }
}
