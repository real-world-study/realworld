package com.study.realworld.domain.tag.api;

import com.study.realworld.domain.tag.application.TagCommandService;
import com.study.realworld.domain.tag.dto.TagSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class TagCommandApi {

    private final TagCommandService tagCommandService;

    @PostMapping("/tags")
    public ResponseEntity<TagSave.Response> save(@Valid @RequestBody final TagSave.Request request) {
        final TagSave.Response response = TagSave.Response.from(tagCommandService.save(request.tagName()));
        return ResponseEntity.ok().body(response);
    }
}
