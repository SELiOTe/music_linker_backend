package com.seliote.mlb.biz.service;

import com.seliote.mlb.biz.domain.si.common.*;
import com.seliote.mlb.biz.domain.so.country.CaptchaSo;
import com.seliote.mlb.common.jsr303.captcha.Uuid;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 通用功能 Service
 *
 * @author seliote
 * @version 2021-08-29
 */
@Validated
public interface CommonService {

    // 图形验证码文本长度
    Integer CAPTCHA_TEXT_LEN = 4;
    // 图形验证码允许的字符范围
    String CAPTCHA_TEXT_RANGE = "qazwsxedcrfvtgbyhnujmkpQAZWSXEDCRFVTGBYHNUJMKP23456789";

    // 短信验证码文本长度
    Integer VERIFY_CODE_LEN = 6;

    /**
     * 创建一个图形验证码，创建失败时抛出 ApiException
     *
     * @return 图形验证码 SO
     */
    @Valid
    @NotNull
    CaptchaSo createCaptcha();

    /**
     * 校验图形验证码是否正确
     *
     * @param si 请求 SI
     * @return 正确返回 true，否则返回 false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean checkCaptcha(@NotNull @Valid CheckCaptchaSi si);

    /**
     * 移除一个验证码，验证码不存在时不执行任何操作
     *
     * @param uuid 验证码 UUID
     */
    void removeCaptcha(@Uuid String uuid);

    /**
     * 发送注册短信验证码
     *
     * @param si 请求 SI
     * @return 发送成功返回 true，否则返回 false
     */
    boolean sendSignUpSms(@NotNull @Valid SendSignUpSmsSi si);

    /**
     * 校验注册短信验证码
     *
     * @param si 请求 SI
     * @return 校验成功返回 true，否则返回 false
     */
    boolean checkSignUpSms(@NotNull @Valid CheckSignUpSmsSi si);

    /**
     * 移除注册短信验证码
     *
     * @param si 请求 SI
     */
    void removeSignUpSms(@NotNull @Valid RemoveSignUpSmsSi si);

    /**
     * 发送信任设备短信验证码
     *
     * @param si 请求 SI
     * @return 发送成功返回 true，否则返回 false
     */
    boolean sendTrustDeviceSms(@NotNull @Valid SendTrustDeviceSmsSi si);
}
