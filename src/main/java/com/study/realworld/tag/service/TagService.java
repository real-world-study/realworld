package com.study.realworld.tag.service;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.tag.domain.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tag> refreshTagByExistedTag(List<Tag> tags) {
        return tags.stream()
            .map(tag -> findByTag(tag).orElse(tag))
            .collect(Collectors.toList());
    }

    private Optional<Tag> findByTag(Tag tag) {
        return tagRepository.findByName(tag.name());
    }

}
