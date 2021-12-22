package com.study.realworld.domain.tag.api;

import com.study.realworld.domain.tag.error.TagErrorCode;
import com.study.realworld.domain.tag.error.TagErrorResponse;
import com.study.realworld.domain.tag.error.exception.TagBusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TagApiAdvice {

    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler(TagBusinessException.class)
    protected ResponseEntity<TagErrorResponse> handleTagBusinessException(final TagBusinessException tagBusinessException) {

        log.error("TagBusinessException: {}", tagBusinessException.getMessage());

        final TagErrorCode tagErrorCode = TagErrorCode.values(tagBusinessException);
        final TagErrorResponse tagErrorResponse = TagErrorResponse.from(tagErrorCode);
        return tagErrorResponse.toResponseEntity();
    }
}
