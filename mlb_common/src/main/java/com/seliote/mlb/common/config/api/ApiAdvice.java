package com.seliote.mlb.common.config.api;

import com.seliote.mlb.common.domain.resp.Resp;
import com.seliote.mlb.common.exception.ApiException;
import com.seliote.mlb.common.exception.AuthException;
import com.seliote.mlb.common.exception.FreqException;
import com.seliote.mlb.common.exception.MlbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.seliote.mlb.common.domain.resp.Resp.*;

/**
 * API Advice 全局异常处理器
 *
 * @author seliote
 * @version 2021-07-03
 */
@Slf4j
@ControllerAdvice
public class ApiAdvice {

    /**
     * 未捕获 ApiException 处理器，用于抛出 API 内部业务异常码
     *
     * @param exception ApiException 对象
     * @return 响应实体
     */
    @ExceptionHandler({ApiException.class})
    @ResponseBody
    public Resp<Void> handle(ApiException exception) {
        log.error("Api exception", exception);
        return Resp.resp(SYSTEM_UNKNOWN_EXCEPTION_CODE, SYSTEM_UNKNOWN_EXCEPTION_MSG);
    }

    /**
     * 未捕获 NoHandlerFoundException 处理器
     *
     * @param exception NoHandlerFoundException 对象
     * @return 响应实体
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public Resp<Void> handle(NoHandlerFoundException exception) {
        log.warn("404 not found, {}", exception.getMessage());
        return Resp.resp(NOT_FOUND_404_CODE, NOT_FOUND_404_MSG);
    }

    /**
     * 未捕获 AuthException 处理器
     *
     * @param exception AuthException 对象
     * @return 响应实体
     */
    @ExceptionHandler({AuthException.class})
    @ResponseBody
    public Resp<Void> handle(AuthException exception) {
        log.warn("Authorization exception {}", exception.getMessage());
        return Resp.resp(AUTH_EXCEPTION_CODE, AUTH_EXCEPTION_MSG);
    }

    /**
     * 未捕获 FreqException 处理器
     *
     * @param exception FreqException 对象
     * @return 响应实体
     */
    @ExceptionHandler({FreqException.class})
    @ResponseBody
    public Resp<Void> handle(FreqException exception) {
        log.warn("Frequency check exception, {}", exception.getMessage());
        return Resp.resp(ILLEGAL_FREQ_CODE, ILLEGAL_FREQ_MSG);
    }

    /**
     * 未捕获 BindException 处理器(MethodArgumentNotValidException 继承自此)
     *
     * @param exception BindException 对象
     * @return 响应实体
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public Resp<Void> handle(BindException exception) {
        log.warn("Parameters bind exception", exception);
        return Resp.resp(PARAM_EXCEPTION_CODE, PARAM_EXCEPTION_MSG);
    }

    /**
     * 未捕获 HttpMessageNotReadableException 处理器
     *
     * @param exception HttpMessageNotReadableException 对象
     * @return 响应实体
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Resp<Void> handle(HttpMessageNotReadableException exception) {
        log.warn("Parameters not readable exception", exception);
        return Resp.resp(PARAM_EXCEPTION_CODE, PARAM_EXCEPTION_MSG);
    }

    /**
     * 未捕获 MlbException 处理器
     *
     * @param exception MlbException 对象
     * @return 响应实体
     */
    @ExceptionHandler({MlbException.class})
    @ResponseBody
    public Resp<Void> handle(MlbException exception) {
        log.warn("MLB system exception", exception);
        return Resp.resp(SYSTEM_UNKNOWN_EXCEPTION_CODE, SYSTEM_UNKNOWN_EXCEPTION_MSG);
    }

    /**
     * 未捕获 Exception 处理器
     *
     * @param exception Exception 对象
     * @return 响应实体
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Resp<Void> handle(Exception exception) {
        log.warn("Unknown exception", exception);
        return Resp.resp(UNKNOWN_EXCEPTION_CODE, UNKNOWN_EXCEPTION_MSG);
    }
}
