package com.study.realworld.domain.tag.util;

import com.study.realworld.domain.tag.domain.persist.Tag;
import com.study.realworld.domain.tag.domain.vo.TagName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagFixture {
    public static final TagName TAG_NAME_REACT_JS = TagName.from("reactjs");
    public static final TagName TAG_NAME_ANGULAR_JS = TagName.from("angularjs");
    public static final TagName TAG_NAME_DRAGONS = TagName.from("dragons");

    public static final List<TagName> TAG_NAMES = List.of(TAG_NAME_REACT_JS, TAG_NAME_ANGULAR_JS, TAG_NAME_DRAGONS);

    public static Tag createTag(final TagName tagName) {
        return new Tag(tagName);
    }

    public static List<Tag> createTags(final TagName... tagName) {
        return Arrays.stream(tagName).map(it -> new Tag(it)).collect(Collectors.toList());
    }
}
