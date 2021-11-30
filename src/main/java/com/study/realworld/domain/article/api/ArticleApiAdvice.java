package com.study.realworld.domain.article.api;

import com.study.realworld.domain.article.error.ArticleErrorCode;
import com.study.realworld.domain.article.error.ArticleErrorResponse;
import com.study.realworld.domain.article.error.exception.ArticleBusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArticleApiAdvice {

    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler(ArticleBusinessException.class)
    protected ResponseEntity<ArticleErrorResponse> handleArticleBusinessException(final ArticleBusinessException articleBusinessException) {

        log.error("ArticleBusinessException: {}", articleBusinessException.getMessage());

        final ArticleErrorCode articleErrorCode = ArticleErrorCode.values(articleBusinessException);
        final ArticleErrorResponse articleErrorResponse = ArticleErrorResponse.from(articleErrorCode);
        return articleErrorResponse.toResponseEntity();
    }
}
