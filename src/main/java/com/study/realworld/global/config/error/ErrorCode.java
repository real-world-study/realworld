package com.study.realworld.global.config.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus httpStatus();
    String message();

}
