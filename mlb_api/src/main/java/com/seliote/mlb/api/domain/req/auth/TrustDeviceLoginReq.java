package com.seliote.mlb.api.domain.req.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import com.seliote.mlb.common.jsr303.userinfo.PlaintextPassword;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;
import lombok.ToString;

/**
 * 受信设备登录 Req
 *
 * @author seliote
 * @version 2021-09-06
 */
@Data
public class TrustDeviceLoginReq {

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

    // 设备码
    @JsonProperty("device_no")
    @DeviceNo
    private String deviceNo;
}
