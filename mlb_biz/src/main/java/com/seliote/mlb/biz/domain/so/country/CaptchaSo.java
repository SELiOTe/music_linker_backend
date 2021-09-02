package com.seliote.mlb.biz.domain.so.country;

import com.seliote.mlb.common.jsr303.captcha.CaptchaImg;
import com.seliote.mlb.common.jsr303.captcha.Uuid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图形验证码 SO
 *
 * @author seliote
 * @version 2021-08-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaSo {

    // 验证码 UUID
    @Uuid
    private String uuid;

    // 图形验证码 Base64
    @CaptchaImg
    private String captchaImg;
}
