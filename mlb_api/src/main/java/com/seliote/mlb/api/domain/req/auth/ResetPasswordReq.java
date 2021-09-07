package com.seliote.mlb.api.domain.req.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.phone.VerifyCode;
import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import com.seliote.mlb.common.jsr303.userinfo.PlaintextPassword;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;
import lombok.ToString;

/**
 * 重置密码 Req
 *
 * @author seliote
 * @version 2021-09-07
 */
@Data
public class ResetPasswordReq {

    // 国际电话区号
    @JsonProperty("phone_code")
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @JsonProperty("tel_no")
    @TelNo
    private String telNo;

    // 明文密码
    @ToString.Exclude
    @JsonProperty("password")
    @PlaintextPassword
    private String password;

    // 短信验证码
    @JsonProperty("verify_code")
    @VerifyCode
    private String verifyCode;

    // 设备码
    @JsonProperty("device_no")
    @DeviceNo
    private String deviceNo;
}
