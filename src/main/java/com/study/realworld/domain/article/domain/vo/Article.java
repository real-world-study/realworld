package com.study.realworld.domain.article.domain.vo;

import com.study.realworld.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;
    private String title;
    private String description;
    private String body;

    @Builder
    public Article(final String slug, final String title, final String description, final String body) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
    }
}
