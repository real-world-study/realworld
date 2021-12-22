package com.study.realworld.global.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class PerformanceTimeTracer {
    private static final String DEBUG_MESSAGE = "Request: {} {}{} < {} ({}ms)";
    private static final Logger performanceTimeTraceLogger = LoggerFactory.getLogger(PerformanceTimeTracer.class);

    @Around("within(com.study.realworld.domain.follow.application..*)")
    public Object calculatePerformanceTimeMember(ProceedingJoinPoint joinPoint) throws Throwable {
        return object(joinPoint);
    }

    private Object object(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = httpServletRequest();
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log(joinPoint, request, timeMs);
        }
    }

    private HttpServletRequest httpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    private void log(ProceedingJoinPoint joinPoint, HttpServletRequest request, long timeMs) {
        performanceTimeTraceLogger.info(DEBUG_MESSAGE, request.getMethod(), request.getRequestURI(),
                Arrays.toString(joinPoint.getArgs()), request.getRemoteHost(), timeMs);
    }
}
