package com.seliote.mlb.common.config.api;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * API 频率限制注解
 *
 * @author seliote
 * @version 2021-07-03
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFreq {
    // 限制类型
    // Body 类型只支持 POST 请求，且只能包含一个参数
    ApiFreqType type() default ApiFreqType.HEADER;

    // 限制字段
    String keys() default "Authorization";

    // 限制字段分隔符
    String keySeparator() default ",";

    // 最大频率（包含）
    int freq() default 5;

    // 时间
    long time() default 1;

    // 单位
    ChronoUnit unit() default ChronoUnit.MINUTES;
}
