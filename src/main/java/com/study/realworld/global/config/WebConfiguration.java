package com.study.realworld.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private static final String PAGE_PARAMETER_NAME = "offset";
    private static final String SIZE_PARAMETER_NAME = "limit";

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        final SortHandlerMethodArgumentResolver sortArgumentResolver = new SortHandlerMethodArgumentResolver();
        final PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver(sortArgumentResolver);
        pageableArgumentResolver.setFallbackPageable(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE));
        pageableArgumentResolver.setPageParameterName(PAGE_PARAMETER_NAME);
        pageableArgumentResolver.setSizeParameterName(SIZE_PARAMETER_NAME);
        argumentResolvers.add(pageableArgumentResolver);
    }
}
