package com.study.realworld.domain.follow.error.exception;

public class FollowNullPointerException extends FollowBusinessException {

    private static final String MESSAGE = "Follow 가 null 입니다.";

    public FollowNullPointerException() {
        super(MESSAGE);
    }

}