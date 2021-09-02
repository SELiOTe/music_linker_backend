package com.seliote.mlb.common.exception;

/**
 * Redis 相关异常
 *
 * @author seliote
 * @version 2021-07-03
 */
@SuppressWarnings("unused")
public class RedisException extends MlbException {

    public RedisException() {
        super();
    }

    public RedisException(String message) {
        super(message);
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    protected RedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
