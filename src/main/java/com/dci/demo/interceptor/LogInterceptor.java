package com.dci.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private final static Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        log.debug("URI Requested: " + request.getRequestURI() + ", UserName: " + userName + ", Origin: " + request.getHeader("origin") + ", " +
                "Referer: " + request.getHeader("referer") + ", Remote Host: " + request.getRemoteHost());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        long startTime = (Long) request.getAttribute("startTime");
        long completionTime = (System.currentTimeMillis() - startTime);

        log.debug("Time taken for Request URI '" + request.getRequestURI() + "' was: " + completionTime + " milliseconds");
    }
}