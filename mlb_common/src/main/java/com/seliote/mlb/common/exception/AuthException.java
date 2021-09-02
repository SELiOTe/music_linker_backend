package com.seliote.mlb.common.exception;

/**
 * 认证与授权异常
 *
 * @author seliote
 * @version 2021-07-04
 */
@SuppressWarnings("unused")
public class AuthException extends MlbException {

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    protected AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
