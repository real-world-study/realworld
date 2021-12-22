package com.study.realworld.domain.follow.error.exception;

public class DuplicatedFollowException extends FollowBusinessException {

    private static final String DUPLICATED_FOLLOW_MESSAGE = "팔로우 정보가 이미 있습니다";

    public DuplicatedFollowException() {
        super(DUPLICATED_FOLLOW_MESSAGE);
    }
}
