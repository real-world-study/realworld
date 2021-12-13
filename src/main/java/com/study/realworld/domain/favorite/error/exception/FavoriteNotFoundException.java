package com.study.realworld.domain.favorite.error.exception;

public class FavoriteNotFoundException extends FavoriteBusinessException {
    private static final String MESSAGE = "좋아요 정보를 찾을 수 없습니다";

    public FavoriteNotFoundException() {
        super(MESSAGE);
    }
}
