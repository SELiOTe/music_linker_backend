package com.seliote.mlb.api.domain.resp.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.userinfo.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 不受信设备登录 Resp
 *
 * @author seliote
 * @version 2021-09-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustlessDeviceLoginResp {

    // 用户鉴权 Token
    @JsonProperty("token")
    @Token
    String token;
}
