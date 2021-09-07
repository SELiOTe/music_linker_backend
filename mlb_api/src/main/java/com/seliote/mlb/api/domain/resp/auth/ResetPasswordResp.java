package com.seliote.mlb.api.domain.resp.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.userinfo.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 重置密码 Resp
 *
 * @author seliote
 * @version 2021-09-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordResp {

    // 用户鉴权 Token
    @JsonProperty("token")
    @Token
    String token;
}
