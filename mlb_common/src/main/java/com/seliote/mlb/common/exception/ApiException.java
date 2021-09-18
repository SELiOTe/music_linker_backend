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

    // API 异常信息
    private final String msg;

    public ApiException(String msg) {
        this.msg = msg;
    }
}
