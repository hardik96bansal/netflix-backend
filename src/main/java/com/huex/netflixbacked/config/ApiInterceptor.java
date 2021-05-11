package com.huex.netflixbacked.config;

import com.huex.netflixbacked.exceptions.UnautorizedAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ApiInterceptor extends HandlerInterceptorAdapter {

    private static final String HEADER_TIME = "X_TIME_TO_EXECUTE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String value = request.getHeader("X-Auth-Token");

        if(value == null){
            throw new UnautorizedAccessException("Unauthorized Access Attempt: Token Not Found. Please sign in again and continue");
        }

        String currentRequestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        request.setAttribute("requestId", currentRequestId);
        return true;
    }

    public static HttpHeaders getResponseHttpHeader(LocalDateTime startTime){
        LocalDateTime endTime = LocalDateTime.now();
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("X-TIME-TO-EXECUTE",String.valueOf(ChronoUnit.MILLIS.between(startTime, endTime)));
        return responseHeader;

    }
}
