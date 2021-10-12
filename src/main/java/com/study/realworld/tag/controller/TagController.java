package com.study.realworld.tag.controller;

import com.study.realworld.tag.controller.response.TagsResponse;
import com.study.realworld.tag.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<TagsResponse> getTags() {
        return ResponseEntity.ok().body(TagsResponse.of(tagService.findAll()));
    }

}
