package com.study.realworld.article.dto.request;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.SlugTitle;
import com.study.realworld.article.domain.vo.Title;
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
