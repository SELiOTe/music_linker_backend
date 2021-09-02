package com.seliote.mlb.common.exception;

/**
 * 工具类异常
 *
 * @author seliote
 * @version 2021-06-27
 */
@SuppressWarnings("unused")
public class UtilException extends MlbException {

    public UtilException() {
        super();
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    protected UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
