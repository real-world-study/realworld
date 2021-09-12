package com.study.realworld.domain.user.error.exception;

public class EmailNotFoundException extends UserBusinessException {

    private static final String MESSAGE = "이메일 : [ %s ] 를 찾을 수 없습니다.";

    public EmailNotFoundException(final String email) {
        super(String.format(MESSAGE, email));
    }

}
