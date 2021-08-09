package com.study.realworld.common;

public enum Errors {
    EXCEPTION(-1, "통신중 에러가 발생했습니다."),
    NONE(0, "이상없음"),
    INVALID_REQUEST(1, "잘못된 요청입니다."),
    DB(2, "데이터 베이스 오류입니다."),
    SAME_NICKNAME(3, "동일 닉네임유저가 존재합니다."),
    SAME_EMAIL(4, "동일 이메일유저가 존재합니다."),

    NOT_FOUND_SESSION_USER(10000, "유저 정보를 찾을 수 없습니다."),

    SQLEXCEPTION(-2, "서버와 통신중 에러가 발생했습니다."),
    ;

    private int code;
    private String desc;

    Errors(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Errors parse(int code) {
        for (Errors e : Errors.values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return INVALID_REQUEST;
    }
}