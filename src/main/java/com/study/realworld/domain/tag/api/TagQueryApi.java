package com.study.realworld.domain.tag.api;

import com.study.realworld.domain.tag.domain.persist.TagQuerydsl;
import com.study.realworld.domain.tag.dto.TagFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TagQueryApi {

    private final TagQuerydsl tagQuerydsl;

    @GetMapping("/tags")
    public ResponseEntity<TagFindResponse> findAll() {
        final TagFindResponse response = TagFindResponse.from(tagQuerydsl.findAll());
        return ResponseEntity.ok().body(response);
    }
}
