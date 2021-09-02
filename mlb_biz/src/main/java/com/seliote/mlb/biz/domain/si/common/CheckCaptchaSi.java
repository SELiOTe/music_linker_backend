package com.seliote.mlb.biz.domain.si.common;

import com.seliote.mlb.common.jsr303.captcha.Captcha;
import com.seliote.mlb.common.jsr303.captcha.Uuid;
import lombok.Data;

/**
 * 校验图形验证码是否正确 SI
 *
 * @author seliote
 * @version 2021-09-02
 */
@Data
public class CheckCaptchaSi {

    // 验证码 UUID
    @Uuid
    private String uuid;

    // 验证码文本
    @Captcha
    private String captcha;
}
