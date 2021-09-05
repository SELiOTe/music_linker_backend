package com.seliote.mlb.api.domain.req.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

/**
 * 是否为受信任设备 Req
 *
 * @author seliote
 * @version 2021-09-05
 */
@Data
public class IsTrustDeviceReq {

    // 国际电话区号
    @JsonProperty("phone_code")
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @JsonProperty("tel_no")
    @TelNo
    private String telNo;

    // 设备串号
    @JsonProperty("device_no")
    @DeviceNo
    private String deviceNo;
}
