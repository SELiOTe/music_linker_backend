package com.seliote.mlb.common.config.api;

import org.aspectj.lang.annotation.Pointcut;

/**
 * API 切点
 *
 * @author seliote
 * @version 2021-07-03
 */
public class ApiPointcut {

    /**
     * API 切点，只支持 POST 请求
     */
    @Pointcut("(@within(org.springframework.stereotype.Controller) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "&& @annotation(org.springframework.web.bind.annotation.ResponseBody) " +
            "&& execution(public * com.seliote.mlb..*.*(..))) " +
            "|| (@within(org.springframework.web.bind.annotation.RestController) " +
            "&& @annotation(org.springframework.web.bind.annotation.PostMapping))")
    public void postEndpoints() {
    }
}
