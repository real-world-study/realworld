package com.study.realworld.domain.user.error.exception;

public class DuplicatedEmailException extends UserBusinessException {

    private static final String MESSAGE = "이메일 : [ %s ] 가 이미 존재합니다.";

    public DuplicatedEmailException(final String email) {
        super(String.format(MESSAGE, email));
    }
}
