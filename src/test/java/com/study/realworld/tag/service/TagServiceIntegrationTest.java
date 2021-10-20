package com.study.realworld.tag.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.tag.domain.TagRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class TagServiceIntegrationTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Test
    @DisplayName("이미 존재한 tags를 가지고 영속성 refresh할 수 있다.")
    void refreshTagByExistedTagTest() {

        // given
        Tag tag = Tag.of("tag1");
        Tag expected = tagRepository.save(tag);

        Tag tag1 = Tag.of("tag1");
        List<Tag> tags = Arrays.asList(tag1);

        // when
        List<Tag> result = tagService.refreshTagByExistedTag(tags);

        // then
        assertAll(
            () -> assertSame(result.get(0), expected),
            () -> assertNotSame(result.get(0), tag1)
        );
    }

}
