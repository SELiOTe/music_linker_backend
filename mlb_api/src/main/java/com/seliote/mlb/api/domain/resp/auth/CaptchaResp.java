package com.seliote.mlb.api.domain.resp.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.captcha.CaptchaImg;
import com.seliote.mlb.common.jsr303.captcha.Uuid;
import lombok.Data;

/**
 * 图形验证码 Resp
 *
 * @author seliote
 * @version 2021-08-29
 */
@Data
public class CaptchaResp {

    // 验证码 UUID
    @JsonProperty("uuid")
    @Uuid
    private String uuid;

    // 图形验证码 Base64
    @JsonProperty("captcha_img")
    @CaptchaImg
    private String captchaImg;
}
