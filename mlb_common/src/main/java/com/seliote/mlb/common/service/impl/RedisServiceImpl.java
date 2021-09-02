package com.seliote.mlb.common.service.impl;

import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.exception.RedisException;
import com.seliote.mlb.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * Redis Service 实现
 *
 * @author seliote
 * @version 2021-07-03
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;
    private final String namespace;
    private final String keySeparator;

    @Autowired
    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate, YmlConfig ymlConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.namespace = ymlConfig.getRedis().getNamespace();
        this.keySeparator = ymlConfig.getRedis().getKeySeparator();
    }

    @Override
    public Optional<String> get(String... keys) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(formatKey(keys)));
    }

    @Override
    public void set(Duration duration, String value, String... keys) {
        stringRedisTemplate.opsForValue().set(formatKey(keys), value, duration);
    }

    @Override
    public Optional<Long> incr(String... keys) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().increment(formatKey(keys)));
    }

    @Override
    public void expire(Duration duration, String... keys) {
        stringRedisTemplate.expire(formatKey(keys), duration);
    }

    @Override
    public void remove(@NotEmpty String... keys) {
        stringRedisTemplate.delete(formatKey(keys));
    }

    /**
     * 格式化 key，添加命名空间等
     *
     * @param keys 需要格式化的 key
     * @return 格式化后的 key
     */
    @NonNull
    private String formatKey(@NonNull String... keys) {
        if (keys.length == 0) {
            throw new RedisException("Can not format empty key set");
        }
        var sb = new StringBuilder();
        sb.append(namespace).append(keySeparator);
        Arrays.stream(keys).forEach(k -> sb.append(k).append(keySeparator));
        var key = sb.substring(0, sb.length() - keySeparator.length());
        log.trace("Format redis key {}", key);
        return key;
    }
}
