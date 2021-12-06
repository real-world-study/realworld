package com.study.realworld.domain.tag.domain.persist;

import com.study.realworld.domain.tag.domain.vo.TagName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByTagName(final TagName tagName);

    Optional<Tag> findByTagName(final TagName tagName);
}
