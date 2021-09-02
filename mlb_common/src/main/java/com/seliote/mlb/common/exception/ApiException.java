package com.seliote.mlb.common.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * API 相关异常
 *
 * @author seliote
 * @version 2021-08-22
 */
@Slf4j
@SuppressWarnings("unused")
@Getter
public class ApiException extends MlbException {

    // API 异常响应码
    private final Integer code;

    // API 异常信息
    private final String msg;

    public ApiException(Integer code, String msg) {
        if (code == null || code <= 0 || msg == null) {
            log.error("Can not construct api exception for code {} message {}, " +
                    "code must bigger then 0, and message can not be null", code, msg);
            throw new MlbException("ApiException construct failed");
        }
        this.code = code;
        this.msg = msg;
    }
}
