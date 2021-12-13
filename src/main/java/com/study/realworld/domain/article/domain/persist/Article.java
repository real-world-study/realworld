package com.study.realworld.domain.article.domain.persist;

import com.study.realworld.domain.article.domain.vo.*;
import com.study.realworld.domain.article.strategy.SlugStrategy;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Where(clause = "activated = true")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long articleId;

    @Embedded
    private ArticleSlug articleSlug;

    @Embedded
    private ArticleTitle articleTitle;

    @Embedded
    private ArticleBody articleBody;

    @Embedded
    private ArticleDescription articleDescription;

    @Embedded
    private ArticleTags articleTags = new ArticleTags();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false, updatable = false)
    private User author;

    @Column(name = "activated")
    private boolean activated = true;

    @Builder
    public Article(final ArticleSlug articleSlug, final ArticleTitle articleTitle,
                   final ArticleBody articleBody, final ArticleDescription articleDescription,
                   final ArticleTags articleTags, final User author) {
        this.articleSlug = articleSlug;
        this.articleTitle = articleTitle;
        this.articleBody = articleBody;
        this.articleDescription = articleDescription;
        this.author = author;
    }

    public boolean isAuthor(final User user) {
        return author.equals(user);
    }

    public Long articleId() {
        return articleId;
    }

    public ArticleSlug articleSlug() {
        return articleSlug;
    }

    public ArticleTitle articleTitle() {
        return articleTitle;
    }

    public ArticleBody articleBody() {
        return articleBody;
    }

    public ArticleDescription articleDescription() {
        return articleDescription;
    }

    public ArticleTags articleTags() {
        return articleTags;
    }

    public User author() {
        return author;
    }

    public Article changeAuthor(final User author) {
        this.author = author;
        return this;
    }

    public Article changeArticleTitleAndSlug(final ArticleTitle articleTitle, final SlugStrategy slugStrategy) {
        final String articleSlug = slugStrategy.mapToSlug(articleTitle.articleTitle());
        return changeArticleTitle(articleTitle)
                .changeArticleSlug(ArticleSlug.from(articleSlug));
    }

    private Article changeArticleTitle(final ArticleTitle articleTitle) {
        this.articleTitle = articleTitle;
        return this;
    }

    private Article changeArticleSlug(final ArticleSlug articleSlug) {
        this.articleSlug = articleSlug;
        return this;
    }

    public Article changeArticleBody(final ArticleBody articleBody) {
        this.articleBody = articleBody;
        return this;
    }

    public Article changeArticleDescription(final ArticleDescription articleDescription) {
        this.articleDescription = articleDescription;
        return this;
    }

    public Article addArticleTags(final List<ArticleTag> articleTags) {
        this.articleTags.addArticleTags(articleTags);
        return this;
    }

    public void delete() {
        activated = false;
        recordDeletedTime(LocalDateTime.now());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Article article = (Article) o;
        return Objects.equals(articleId(), article.articleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId());
    }

}
