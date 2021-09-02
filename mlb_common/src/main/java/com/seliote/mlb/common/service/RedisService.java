package com.seliote.mlb.common.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Optional;

/**
 * Redis Service
 * 只支持 String 相关类型
 * 如需存储对象请 JSON 序列化
 *
 * @author seliote
 * @version 2021-07-03
 */
@Validated
public interface RedisService {

    /**
     * 获取 redis 存储的 String
     *
     * @param keys Redis Key
     * @return redis 存储的数据，不存在时返回空
     */
    Optional<String> get(@NotEmpty String... keys);

    /**
     * 设置带过期时间的数据 String
     *
     * @param duration 过期时间
     * @param value    值
     * @param keys     Redis Key
     */
    void set(@NotNull Duration duration, @NotNull String value, @NotEmpty String... keys);

    /**
     * 递增一个键
     *
     * @param keys Redis Key
     */
    Optional<Long> incr(@NotEmpty String... keys);

    /**
     * 设置键的过期时间
     *
     * @param duration 过期时间
     * @param keys     Redis Key
     */
    void expire(@NotNull Duration duration, @NotEmpty String... keys);

    /**
     * 删除一个 Redis String
     *
     * @param keys Redis Key
     */
    void remove(@NotEmpty String... keys);
}
