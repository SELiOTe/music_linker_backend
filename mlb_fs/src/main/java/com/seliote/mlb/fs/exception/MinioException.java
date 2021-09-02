package com.seliote.mlb.fs.exception;

import com.seliote.mlb.common.exception.MlbException;

/**
 * Minio 异常
 *
 * @author seliote
 * @version 2021-07-02
 */
@SuppressWarnings("unused")
public class MinioException extends MlbException {

    public MinioException() {
        super();
    }

    public MinioException(String message) {
        super(message);
    }

    public MinioException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioException(Throwable cause) {
        super(cause);
    }

    protected MinioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
