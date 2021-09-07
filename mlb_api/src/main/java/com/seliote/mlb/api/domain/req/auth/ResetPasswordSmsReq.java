package com.seliote.mlb.api.domain.req.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.captcha.Captcha;
import com.seliote.mlb.common.jsr303.captcha.Uuid;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

/**
 * 发送重置密码短信验证码 Req
 *
 * @author seliote
 * @version 2021-09-01
 */
@Data
public class ResetPasswordSmsReq {

    // 图形验证码 UUID
    @JsonProperty("uuid")
    @Uuid
    private String uuid;

    // 图形验证码
    @JsonProperty("captcha")
    @Captcha
    private String captcha;

    // 国际电话区号
    @JsonProperty("phone_code")
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @JsonProperty("tel_no")
    @TelNo
    private String telNo;
}
