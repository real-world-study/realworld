package com.study.realworld.core.util;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author JeongJoon Seo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static String getAccessToken() {
        return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    }
}
