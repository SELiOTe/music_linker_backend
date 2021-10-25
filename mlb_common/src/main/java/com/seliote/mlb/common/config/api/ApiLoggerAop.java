package com.seliote.mlb.common.config.api;

import com.seliote.mlb.common.exception.MlbException;
import com.seliote.mlb.common.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * API 请求与响应日志记录器
 *
 * @author seliote
 * @version 2021-07-03
 */
@Slf4j
@Order(100)
@Component
@Aspect
public class ApiLoggerAop {

    /**
     * API 日志记录器
     *
     * @param proceedingJoinPoint ProceedingJoinPoint 对象
     * @return AOP 返回值
     * @throws Throwable 方法处理过程中抛出
     */
    @Around("com.seliote.mlb.common.config.api.ApiPointcut.endpoints()")
    public Object logger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var ip = NetworkUtils.getRequestIp();
        var uri = NetworkUtils.getRequestUri();
        // 日志记录不到直接抛异常，不处理了，就是这么任性
        if (ip.isEmpty() || uri.isEmpty()) {
            log.error("Can not logger for api, ip {} url {}", ip, uri);
            throw new MlbException("Can not logger for api");
        }
        var args = proceedingJoinPoint.getArgs();
        Object resp = null;
        try {
            resp = proceedingJoinPoint.proceed();
            return resp;
        } finally {
            log.info("{} request {}, arguments {}, response {}",
                    ip.get(), uri.get(), Arrays.deepToString(args), resp == null ? "null" : resp.toString());
        }
    }
}
