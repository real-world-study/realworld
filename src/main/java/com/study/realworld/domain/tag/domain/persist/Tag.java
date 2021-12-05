package com.study.realworld.domain.tag.domain.persist;

import com.study.realworld.domain.tag.domain.vo.TagName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long tagId;

    @Embedded
    private TagName tagName;

    public Long tagId() {
        return tagId;
    }

    public TagName tagName() {
        return tagName;
    }

    public Tag(final TagName tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Tag tag = (Tag) o;
        return Objects.equals(tagId(), tag.tagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId());
    }
}
