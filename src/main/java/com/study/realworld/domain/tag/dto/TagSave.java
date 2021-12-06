package com.study.realworld.domain.tag.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.domain.tag.domain.persist.Tag;
import com.study.realworld.domain.tag.domain.vo.TagName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class TagSave {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {

        @JsonProperty("tag")
        private TagName tagName;

        public static Request from(final TagName tagName) {
            return new Request(tagName);
        }

        public Tag toEntity() {
            return new Tag(tagName);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {

        @JsonProperty("tag")
        private TagName tagName;

        public static Response from(final Tag tag) {
            return new Response(tag.tagName());
        }

        public TagName tagName() {
            return tagName;
        }
    }
}
