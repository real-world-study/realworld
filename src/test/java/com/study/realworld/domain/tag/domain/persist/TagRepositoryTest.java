package com.study.realworld.domain.tag.domain.persist;

import com.study.realworld.domain.tag.domain.vo.TagName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TagRepository tagRepository;

    @AfterEach
    void afterEach() {
        testEntityManager.clear();
    }

    @Test
    void 태그_존재시_존재_여부를_반환한다() {
        final TagName tagName = TagName.from("tagName");
        final Tag tag = new Tag(tagName);

        testEntityManager.persist(tag);

        assertThat(tagRepository.existsByTagName(tagName)).isTrue();
    }

    @Test
    void 태그_미존재시_존재_여부를_반환한다() {
        final TagName tagName = TagName.from("tagName");

        assertThat(tagRepository.existsByTagName(tagName)).isFalse();
    }
}
