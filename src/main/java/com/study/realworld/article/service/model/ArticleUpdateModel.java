package com.study.realworld.article.service.model;

import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.Title;
import java.util.Optional;

public class ArticleUpdateModel {

    private final Title title;
    private final Description description;
    private final Body body;

    public ArticleUpdateModel(Title title, Description description, Body body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public Optional<Title> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Body> getBody() {
        return Optional.ofNullable(body);
    }

}
