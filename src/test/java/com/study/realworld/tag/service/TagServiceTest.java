package com.study.realworld.tag.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.tag.domain.TagRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    @DisplayName("Tag 전체 List를 반환할 수 있다.")
    void findAllTest() {

        // setup & given
        List<Tag> tags = Arrays.asList(Tag.of("tag1"), Tag.of("tag2"));
        when(tagRepository.findAll()).thenReturn(tags);

        // when
        List<Tag> result = tagService.findAll();

        // then
        assertThat(result).isEqualTo(tags);
    }

}