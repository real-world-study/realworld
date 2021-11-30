package com.study.realworld.domain.follow.api;

import com.study.realworld.domain.follow.error.FollowErrorCode;
import com.study.realworld.domain.follow.error.FollowErrorResponse;
import com.study.realworld.domain.follow.error.exception.FollowBusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FollowApiAdvice {

    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler(FollowBusinessException.class)
    protected ResponseEntity<FollowErrorResponse> handleFollowBusinessException(final FollowBusinessException followBusinessException) {

        log.error("FollowBusinessException: {}", followBusinessException.getMessage());

        final FollowErrorCode followErrorCode = FollowErrorCode.values(followBusinessException);
        final FollowErrorResponse followErrorResponse = FollowErrorResponse.from(followErrorCode);
        return followErrorResponse.toResponseEntity();
    }
}
