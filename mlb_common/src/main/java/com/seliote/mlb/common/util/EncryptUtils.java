package com.seliote.mlb.common.util;

import com.seliote.mlb.common.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密相关工具类
 *
 * @author seliote
 * @version 2021-07-03
 */
@Slf4j
public class EncryptUtils {

    /**
     * SHA-256 摘要
     *
     * @param input 数据源
     * @return SHA-256 摘要
     */
    @NonNull
    public static String sha2(@NonNull byte[] input) {
        final var algorithm = "SHA-256";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException exception) {
            log.error("Can not create MessageDigest instance, {}", exception.getMessage());
            throw new UtilException(exception);
        }
        var output = md.digest(input);
        var integer = new BigInteger(1, output);
        var hex = integer.toString(16);
        log.trace("SHA-256 source length {}, result {}", input.length, hex);
        return hex;
    }

    /**
     * SHA-256 以 UTF-8 编码的字符串
     *
     * @param input 数据源字符串
     * @return SHA-256 摘要
     */
    @NonNull
    public static String sha2(@NonNull String input) {
        return sha2(input.getBytes(StandardCharsets.UTF_8));
    }
}
