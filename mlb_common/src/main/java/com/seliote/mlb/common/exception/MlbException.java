package com.seliote.mlb.common.exception;

/**
 * Mlb 基础异常
 *
 * @author seliote
 * @version 2021-06-27
 */
@SuppressWarnings("unused")
public class MlbException extends RuntimeException {

    public MlbException() {
        super();
    }

    public MlbException(String message) {
        super(message);
    }

    public MlbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MlbException(Throwable cause) {
        super(cause);
    }

    protected MlbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
