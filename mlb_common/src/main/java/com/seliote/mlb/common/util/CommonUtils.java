package com.seliote.mlb.common.util;

import com.seliote.mlb.common.exception.UtilException;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * 通用工具类
 *
 * @author seliote
 * @version 2021-08-30
 */
@Slf4j
public class CommonUtils {

    // SecureRandom 对象，线程安全
    private static SecureRandom SECURE_RANDOM;

    /**
     * 获取 Random 实例
     *
     * @return Random 实例
     */
    public static Random getRandom() {
        log.trace("Get SecureRandom instance");
        if (SECURE_RANDOM != null) {
            return SECURE_RANDOM;
        }
        try {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG", "SUN");
            log.trace("Create SecureRandom instance");
            return SECURE_RANDOM;
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("Failed create SecureRandom instance, exception {}, {}",
                    e.getClass().getCanonicalName(), e.getMessage());
            throw new UtilException(e);
        }
    }

    /**
     * Base64 编码
     *
     * @param bytes 数据源字节数组
     * @return Base64 编码后的字符串
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
