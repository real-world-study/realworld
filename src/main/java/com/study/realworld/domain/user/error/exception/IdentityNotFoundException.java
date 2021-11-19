package com.study.realworld.domain.user.error.exception;

public class IdentityNotFoundException extends UserBusinessException {

    private static final String MESSAGE = "식별자 : [ %s ] 를 찾을 수 없습니다.";

    public IdentityNotFoundException(final Long identity) {
        super(String.format(MESSAGE, identity));
    }

}