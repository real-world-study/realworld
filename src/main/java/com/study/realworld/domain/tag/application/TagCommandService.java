package com.study.realworld.domain.tag.application;

import com.study.realworld.domain.tag.domain.persist.Tag;
import com.study.realworld.domain.tag.domain.persist.TagRepository;
import com.study.realworld.domain.tag.domain.vo.TagName;
import com.study.realworld.domain.tag.dto.TagSave;
import com.study.realworld.domain.tag.error.exception.DuplicatedTagNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TagCommandService {

    private final TagRepository tagRepository;

    public Tag save(final TagSave.Request request) {
        final Tag entity = request.toEntity();
        return tagRepository.findByTagName(entity.tagName())
                .orElse(tagRepository.save(entity));
    }

    private void validateDuplicatedTag(final TagName tagName) {
        if (tagRepository.existsByTagName(tagName)) {
            throw new DuplicatedTagNameException(tagName.tagName());
        }
    }
}
