package com.seliote.mlb.common.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 基础响应实体
 *
 * @author seliote
 * @version 2021-06-29
 */
@Slf4j
@Getter
@ToString
public class Resp<T> {

    /**
     * 状态码范围划分：
     * > 0: 业务状态，如验证码不正确
     * = 0: 默认成功
     * -10000 ~ -19999: 系统异常，如 HTTP 请求方法不正确
     * -20000 ~ -29999: 认证与授权异常，如 Token 失效
     * -30000 ~ -39999: 客户端异常，如接口请求频率过快
     */
    // 成功
    public static Integer SUCCESS_CODE = 0;
    public static String SUCCESS_MSG = "success";
    // 未知异常，未处理、未捕获
    public static Integer UNKNOWN_EXCEPTION_CODE = -10000;
    public static String UNKNOWN_EXCEPTION_MSG = "unknown error";
    // 404 not found
    public static Integer NOT_FOUND_404_CODE = -11000;
    public static String NOT_FOUND_404_MSG = "404 not fund";
    // 请求参数异常
    public static Integer PARAM_EXCEPTION_CODE = -12000;
    public static String PARAM_EXCEPTION_MSG = "parameters incorrect";
    // 认证与授权异常
    public static Integer AUTH_EXCEPTION_CODE = -20000;
    public static String AUTH_EXCEPTION_MSG = "authorization exception";
    // 401 未授权
    public static Integer UNAUTHORIZED_CODE = -21000;
    public static String UNAUTHORIZED_MSG = "unauthorized";
    // 403 禁止访问
    public static Integer FORBIDDEN_CODE = -21100;
    public static String FORBIDDEN_MSG = "forbidden";
    // 应用相关未知异常，未处理、未捕获
    public static Integer SYSTEM_UNKNOWN_EXCEPTION_CODE = -30000;
    public static String SYSTEM_UNKNOWN_EXCEPTION_MSG = "system unknown error";
    // 接口频率超限
    public static Integer ILLEGAL_FREQ_CODE = -31000;
    public static String ILLEGAL_FREQ_MSG = "frequency check failed";

    // 响应码
    @JsonProperty("code")
    @NotNull
    private final Integer code;

    // 响应码描述
    @JsonProperty("msg")
    @NotNull
    private final String msg;

    // 响应实际数据，可为空
    @JsonProperty("data")
    @Valid
    private final T data;

    /**
     * protected 构造函数，禁止外部实例化
     *
     * @param code 响应码
     * @param msg  响应码描述
     * @param data 响应实际数据，可为空
     */
    protected Resp(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 创建 Resp 对象
     *
     * @param code 响应码
     * @param msg  响应码描述
     * @param data 响应实际数据
     * @param <T>  响应实际数据泛型类型
     * @return Resp 对象
     */
    public static <T> Resp<T> resp(@NonNull Integer code, @NonNull String msg, @Nullable T data) {
        return new Resp<>(code, msg, data);
    }

    /**
     * 创建默认成功响应
     *
     * @return Resp 对象
     */
    public static <T> Resp<T> resp() {
        return resp(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    /**
     * 创建指定响应码及描述的响应
     *
     * @param code 响应码
     * @param msg  响应码描述
     * @return Resp 对象
     */
    public static <T> Resp<T> resp(@NonNull Integer code, @NonNull String msg) {
        return resp(code, msg, null);
    }

    /**
     * 创建指定响应的默认成功请求
     *
     * @param data 指定响应
     * @param <T>  指定响应泛型
     * @return Resp 对象
     */
    public static <T> Resp<T> resp(@Nullable T data) {
        return resp(SUCCESS_CODE, SUCCESS_MSG, data);
    }
}
