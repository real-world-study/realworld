package com.study.realworld.domain.user.exception;

public class IdentityNotFoundException extends RuntimeException {

    private static final String MESSAGE = "식별자 : [ %s ] 를 찾을 수 없습니다.";

    public IdentityNotFoundException(final Long id) {
        super(String.format(MESSAGE, id));
    }

}