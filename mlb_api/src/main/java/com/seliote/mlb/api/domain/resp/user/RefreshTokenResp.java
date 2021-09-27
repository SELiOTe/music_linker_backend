package com.seliote.mlb.api.domain.resp.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.userinfo.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 刷新当前 Token Resp
 *
 * @author seliote
 * @version 2021-09-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResp {

    // 用户鉴权 Token
    @JsonProperty("token")
    @Token
    String token;
}
