package com.study.realworld.domain.tag.error;

public class DuplicatedTagNameException extends TagBusinessException {

    private static final String MESSAGE = "태그 이름 : [ %s ] 가 이미 존재합니다.";

    public DuplicatedTagNameException(final String email) {
        super(String.format(MESSAGE, email));
    }
}
