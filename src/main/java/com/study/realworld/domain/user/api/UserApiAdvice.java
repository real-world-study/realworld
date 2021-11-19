package com.study.realworld.domain.user.api;

import com.study.realworld.domain.user.error.UserErrorCode;
import com.study.realworld.domain.user.error.UserErrorResponse;
import com.study.realworld.domain.user.error.exception.UserBusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserApiAdvice {

    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler(UserBusinessException.class)
    protected ResponseEntity<UserErrorResponse> handleUserBusinessException(final UserBusinessException userBusinessException) {

        log.error("UserBusinessException: {}", userBusinessException.getMessage());

        final UserErrorCode userErrorCode = UserErrorCode.values(userBusinessException);
        final UserErrorResponse userErrorResponse = UserErrorResponse.from(userErrorCode);
        return userErrorResponse.toResponseEntity();
    }
}
