package com.study.realworld.domain.article.domain.vo;

import com.study.realworld.domain.article.domain.persist.ArticleTag;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class ArticleTags {

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST)
    private List<ArticleTag> articleTags;

    public ArticleTags() {
        this.articleTags = new ArrayList<>();
    }

    public List<ArticleTag> articleTags() {
        return Collections.unmodifiableList(articleTags);
    }

    public void addArticleTags(final List<ArticleTag> articleTags) {
        this.articleTags.addAll(articleTags);
    }
}
