package com.study.realworld.article.controller.request;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.tag.domain.Tag;
import java.util.List;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ArticleCreateRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("body")
    private String body;

    @JsonProperty("tagList")
    private List<String> tags;

    ArticleCreateRequest() {
    }

    public ArticleContent toArticleContent() {
        return ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of(title)))
            .description(Description.of(description))
            .body(Body.of(body))
            .tags(tags.stream().map(Tag::of).collect(toList()))
            .build();
    }

}
