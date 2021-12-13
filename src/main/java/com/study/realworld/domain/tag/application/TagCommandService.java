package com.study.realworld.domain.tag.application;

import com.study.realworld.domain.tag.domain.persist.Tag;
import com.study.realworld.domain.tag.domain.persist.TagRepository;
import com.study.realworld.domain.tag.domain.vo.TagName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TagCommandService {

    private final TagRepository tagRepository;

    public Tag save(final TagName tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }
}
