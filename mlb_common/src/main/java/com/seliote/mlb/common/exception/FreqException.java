package com.seliote.mlb.common.exception;

/**
 * 频率限制相关异常
 *
 * @author seliote
 * @version 2021-07-03
 */
@SuppressWarnings("unused")
public class FreqException extends MlbException {

    public FreqException() {
        super();
    }

    public FreqException(String message) {
        super(message);
    }

    public FreqException(String message, Throwable cause) {
        super(message, cause);
    }

    public FreqException(Throwable cause) {
        super(cause);
    }

    protected FreqException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
