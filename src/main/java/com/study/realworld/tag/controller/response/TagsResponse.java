package com.study.realworld.tag.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;

public class TagsResponse {

    @JsonProperty("tags")
    private List<String> tags;

    protected TagsResponse() {
    }

    public List<String> getTags() {
        return tags;
    }

    private TagsResponse(List<String> tags) {
        this.tags = tags;
    }

    public static TagsResponse of(List<Tag> tags) {
        return new TagsResponse(
            tags.stream()
                .map(Tag::name)
                .collect(Collectors.toList())
        );
    }

}
