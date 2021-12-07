package com.study.realworld.domain.article.domain.vo;

import com.study.realworld.domain.article.domain.persist.ArticleTag;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Embeddable
public class ArticleTags {

    @OneToMany(mappedBy = "article")
    private Set<ArticleTag> articleTags;

    public ArticleTags() {
        this.articleTags = new HashSet<>();
    }

    public void addTag(final ArticleTag articleTag) {
        articleTags.add(articleTag);
    }

    public Set<ArticleTag> articleTags() {
        return Collections.unmodifiableSet(articleTags);
    }
}
