package com.study.realworld.article.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.article.service.model.ArticleUpdateModel;
import java.util.Optional;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ArticleUpdateRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("body")
    private String body;

    ArticleUpdateRequest() {
    }

    public ArticleUpdateModel toArticleUpdateModel() {
        return new ArticleUpdateModel(
            Optional.ofNullable(title).map(Title::of).orElse(null),
            Optional.ofNullable(description).map(Description::of).orElse(null),
            Optional.ofNullable(body).map(Body::of).orElse(null)
        );
    }

}
