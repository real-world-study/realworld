package com.study.realworld.global.config;

import com.study.realworld.domain.article.strategy.SimpleDashBarSlugStrategy;
import com.study.realworld.domain.article.strategy.SlugStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilBeanConfiguration {

    @Bean
    public SlugStrategy slugStrategy() {
        return new SimpleDashBarSlugStrategy();
    }
}
