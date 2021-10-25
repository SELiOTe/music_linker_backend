package com.seliote.mlb.common.config.api;

import com.seliote.mlb.common.exception.FreqException;
import com.seliote.mlb.common.service.RedisService;
import com.seliote.mlb.common.util.EncryptUtils;
import com.seliote.mlb.common.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * API 访问频率限制
 *
 * @author seliote
 * @version 2021-07-03
 */
@Slf4j
@Order(200)
@Component
@Aspect
public class ApiFreqAop {

    // Redis 缓存 key
    private static final String REDIS_KEY = "freq";

    private final RedisService redisService;

    @Autowired
    public ApiFreqAop(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * API 访问频率校验，超限或异常将抛出 FreqException
     *
     * @param joinPoint JoinPoint 对象
     */
    @Before("com.seliote.mlb.common.config.api.ApiPointcut.endpoints() " +
            "&& @annotation(com.seliote.mlb.common.config.api.ApiFreq)")
    public void freq(JoinPoint joinPoint) {
        // 存储调用频率的 Redis Key 为 namespace:freq:${uri}:${identifier}:${unitTime} 过期时间为 period
        var uri = getUri();
        var apiFreq = getFreqAnnotation(joinPoint);
        var identifier = parseIdentifier(joinPoint, apiFreq);
        var unit = calculateUnit(apiFreq);
        var period = Duration.of(apiFreq.time(), apiFreq.unit()).toSeconds();
        checkFrequency(uri, identifier, unit, period, apiFreq.freq());
    }

    /**
     * 获取当前请求的 URI
     *
     * @return 当前请求的 URI
     */
    @NonNull
    private String getUri() {
        var uri = NetworkUtils.getRequestUri();
        if (uri.isEmpty()) {
            log.error("Can not get request URI");
            throw new FreqException("Can not get request URI");
        }
        return uri.get();
    }

    /**
     * 获取切点请求的 @ApiFreq 注解实例
     *
     * @param joinPoint JoinPoint 对象
     * @return @ApiFreq 注解实例
     */
    @NonNull
    private ApiFreq getFreqAnnotation(@NonNull JoinPoint joinPoint) {
        var signature = joinPoint.getSignature();
        if (signature == null) {
            log.error("Can not get signature");
            throw new FreqException("Can not get signature");
        }
        if (!(signature instanceof MethodSignature)) {
            log.error("Can not get MethodSignature, actually type is {}", signature.getClass().getCanonicalName());
            throw new FreqException("Method signature type incorrect");
        }
        var method = ((MethodSignature) signature).getMethod();
        if (method == null) {
            log.error("Can not get method");
            throw new FreqException("Can not get method");
        }
        var annotation = method.getAnnotation(ApiFreq.class);
        if (annotation == null) {
            log.error("Can not get ApiFreq annotation");
            throw new FreqException("Can not get ApiFreq annotation");
        }
        return annotation;
    }

    /**
     * 解析标识符
     *
     * @param joinPoint JoinPoint 对象
     * @param apiFreq   @ApiFreq 注解实例
     * @return 标识符
     */
    @NonNull
    private String parseIdentifier(@NonNull JoinPoint joinPoint, @NonNull ApiFreq apiFreq) {
        var identifier = apiFreq.type() == ApiFreqType.BODY ?
                parseBodyIdentifier(joinPoint, apiFreq) : parseHeaderIdentifier(apiFreq);
        // 防止 Key 过长，Redis key 使用 SHA-256 摘要
        var sha2 = EncryptUtils.sha2(identifier);
        log.debug("Mapping identifier {} to {}", identifier, sha2);
        return sha2;
    }

    /**
     * 解析请求体内的标识符
     *
     * @param joinPoint JoinPoint 对象
     * @param apiFreq   @ApiFreq 注解实例
     * @return 标识符
     */
    @NonNull
    private String parseBodyIdentifier(@NonNull JoinPoint joinPoint, @NonNull ApiFreq apiFreq) {
        var keys = apiFreq.keys().split(apiFreq.keySeparator());
        if (keys.length == 0) {
            log.error("Request body check not specific frequency check key");
            throw new FreqException("Request body not specific frequency check key");
        }
        var args = joinPoint.getArgs();
        if (args == null || args.length != 1) {
            log.error("Body frequency check only support 1 argument");
            throw new FreqException("Body frequency check only support 1 argument");
        }
        var arg = args[0];
        var identifier = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            try {
                // 返回值不为 null，解析失败抛出 IntrospectionException 异常
                final var pd = new PropertyDescriptor(keys[i].trim(), arg.getClass());
                var value = pd.getReadMethod().invoke(arg);
                if (value == null || !StringUtils.hasText(value.toString())) {
                    log.error("Request {} property {} is empty", arg, keys[i]);
                    throw new FreqException("Empty property");
                }
                identifier[i] = value.toString();
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException exception) {
                log.error("Can not parse body property {}, {}", keys[i], exception.getMessage());
                throw new FreqException(exception);
            }
        }
        return String.join(apiFreq.keySeparator(), identifier);
    }

    /**
     * 解析请求头内的标识符
     *
     * @param apiFreq @ApiFreq 注解实例
     * @return 标识符
     */
    @NonNull
    private String parseHeaderIdentifier(@NonNull ApiFreq apiFreq) {
        var keys = apiFreq.keys().split(apiFreq.keySeparator());
        if (keys.length == 0) {
            log.error("Request header check not specific frequency check key");
            throw new FreqException("Request header not specific frequency check key");
        }
        var identifier = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            var value = NetworkUtils.getRequestHeader(keys[i]);
            if (value.isEmpty() || !StringUtils.hasText(value.get())) {
                log.info("Can not get request header {}", keys[i]);
                throw new FreqException("Can not get request header");
            }
            identifier[i] = value.get().trim();
        }
        return String.join(apiFreq.keySeparator(), identifier);
    }

    /**
     * 计算单位起始 UNIX 时间戳秒数
     *
     * @param apiFreq @ApiFreq 注解实例
     * @return 单位起始 UNIX 时间戳秒数
     */
    private long calculateUnit(@NonNull ApiFreq apiFreq) {
        var now = Instant.now().getEpochSecond();
        var period = Duration.of(apiFreq.time(), apiFreq.unit()).toSeconds();
        var unit = (now - (now % period));
        log.debug("Frequency unit {}", unit);
        return unit;
    }

    /**
     * 校验访问频率
     *
     * @param uri        访问 URI
     * @param identifier 用户标识符
     * @param unit       单位起始时间
     * @param period     周期
     * @param freq       单位时间内最大限制次数
     */
    private void checkFrequency(@NonNull String uri,
                                @NonNull String identifier,
                                @NonNull long unit,
                                @NonNull long period,
                                @NonNull long freq) {
        var keys = new String[]{REDIS_KEY, uri, identifier, unit + ""};
        // 直接递增，之前没有存储过的会直接被设置为 1
        var incr = redisService.incr(keys);
        if (incr.isEmpty()) {
            log.error("Unknown error, can not increment redis key {}", Arrays.toString(keys));
            throw new FreqException("Can not increment redis key");
        }
        // 如果是 1 说明递增前是没有的，要设置过期时间
        if (incr.get() == 1L) {
            redisService.expire(Duration.of(period, ChronoUnit.SECONDS), keys);
            log.debug("{} first access {} at {}", identifier, uri, unit);
            return;
        }
        if (incr.get() > freq) {
            log.warn("{} request {} {} times, more then {}", identifier, uri, incr.get(), freq);
            throw new FreqException("Frequency too high");
        }
        log.debug("Allow {} request {} {} times", identifier, uri, incr.get());
    }
}
