package com.study.realworld.domain.tag.domain.persist;

import com.study.realworld.domain.tag.domain.vo.TagName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Tag(태그)")
class TagTest {

    @Test
    void 아이덴티티가_같다면_동일한_객체이다() {
        final Tag tag = new Tag();
        final Tag other = new Tag();

        ReflectionTestUtils.setField(tag, "tagId", 1L);
        ReflectionTestUtils.setField(other, "tagId", 1L);

        assertThat(tag).isEqualTo(other);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 엔티티의_값들은_반환할_수_있다(final String tagNameString) {
        final TagName tagName = TagName.from(tagNameString);
        final Tag tag = new Tag(tagName);

        ReflectionTestUtils.setField(tag, "tagId", 1L);

        assertAll(
                () -> assertThat(tag.tagId()).isEqualTo(1L),
                () -> assertThat(tag.tagName()).isEqualTo(tagName)
        );
    }
}
