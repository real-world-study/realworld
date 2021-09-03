package com.study.realworld.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.study.realworld.exception.CustomException;
import com.study.realworld.exception.ErrorCode;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtil {

    public static String getCurrentUserToken() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        return authentication.getCredentials().toString();
    }
}
