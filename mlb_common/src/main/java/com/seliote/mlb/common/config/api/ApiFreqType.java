package com.seliote.mlb.common.config.api;

/**
 * API 频率限制类型
 *
 * @author seliote
 * @version 2021-07-03
 */
public enum ApiFreqType {
    // HTTP 请求体中, Body 类型只支持 POST 请求，且只能包含一个参数
    BODY,
    // HTTP 请求头
    HEADER
}
