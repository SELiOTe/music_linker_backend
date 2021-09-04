package com.seliote.mlb.api.domain.resp.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.userinfo.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册 Resp
 *
 * @author seliote
 * @version 2021-09-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResp {

    // 用户鉴权 Token
    @JsonProperty("token")
    @Token
    String token;
}
