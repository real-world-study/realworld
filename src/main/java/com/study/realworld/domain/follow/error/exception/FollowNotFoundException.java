package com.study.realworld.domain.follow.error.exception;

public class FollowNotFoundException extends FollowBusinessException {

    private static final String FOLLOW_NOT_FOUND_MESSAGE = "팔로우 정보를 찾을 수 없습니다";

    public FollowNotFoundException() {
        super(FOLLOW_NOT_FOUND_MESSAGE);
    }
}
