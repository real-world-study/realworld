package com.study.realworld.domain.article.error.exception;

public class AuthorMissMatchException extends ArticleBusinessException {
    private static final String ARTICLE_MISS_MATCH_MESSAGE = "수정자와 저자가 다릅니다";

    public AuthorMissMatchException() {
        super(ARTICLE_MISS_MATCH_MESSAGE);
    }
}
