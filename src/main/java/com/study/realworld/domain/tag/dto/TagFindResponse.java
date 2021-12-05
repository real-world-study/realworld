package com.study.realworld.domain.tag.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.domain.tag.domain.persist.Tag;
import com.study.realworld.domain.tag.domain.vo.TagName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagFindResponse {

    @JsonProperty("tags")
    private Set<TagName> tags;

    public static TagFindResponse from(final List<Tag> tags) {
        return new TagFindResponse(tags.stream()
                .map(Tag::tagName)
                .collect(Collectors.toSet()));
    }
}
