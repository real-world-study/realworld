package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public class ArticleSave {

    @Builder
    public static class Request {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("body")
        private String body;

        @JsonProperty("tagList")
        private List<String> tags;

        @Builder
        public Request(final String title, final String description, final String body, final List<String> tags) {
            this.title = title;
            this.description = description;
            this.body = body;
            this.tags = tags;
        }
    }

    public static class Response {
        public String slug() {
            return "how-to-train-your-dragon";
        }

        public String title() {
            return "how to train your dragon";
        }

        public String description() {
            return "Ever wonder how?";
        }

        public String body() {
            return "It takes a Jacobian";
        }

        public List<String> tags() {
            return List.of("dragons", "training");
        }

        public String createdAt() {
            return "2016-02-18T03:22:56.637Z";
        }

        public String updatedAt() {
            return "2016-02-18T03:48:35.824Z";
        }

        public boolean favorited() {
            return false;
        }

        public int favoritesCount() {
            return 0;
        }

        public AuthDto author() {
            return new AuthDto();
        }
    }
}
