package com.seliote.mlb.task.exception;

import com.seliote.mlb.common.exception.MlbException;

/**
 * 任务相关异常
 *
 * @author seliote
 * @version 2021-07-01
 */
@SuppressWarnings("unused")
public class TaskException extends MlbException {

    public TaskException() {
        super();
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }

    protected TaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
