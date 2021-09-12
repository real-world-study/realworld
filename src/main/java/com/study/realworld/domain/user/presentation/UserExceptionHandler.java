package com.study.realworld.domain.user.presentation;

import com.study.realworld.domain.user.error.UserErrorCode;
import com.study.realworld.domain.user.error.UserErrorResponse;
import com.study.realworld.domain.user.error.exception.UserBusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserBusinessException.class)
    protected ResponseEntity<UserErrorResponse> handleUserBusinessException(
            final UserBusinessException userBusinessException) {
        final UserErrorCode userErrorCode = UserErrorCode.values(userBusinessException);
        final UserErrorResponse userErrorResponse = new UserErrorResponse(userErrorCode);
        return userErrorResponse.toResponseEntity();
    }

}
