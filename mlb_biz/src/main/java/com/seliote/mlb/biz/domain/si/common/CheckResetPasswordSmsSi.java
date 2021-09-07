package com.seliote.mlb.biz.domain.si.common;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.phone.VerifyCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

/**
 * 校验重置密码短信验证码 SI
 *
 * @author seliote
 * @version 2021-09-02
 */
@Data
public class CheckResetPasswordSmsSi {

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @TelNo
    private String telNo;

    // 短信验证码
    @VerifyCode
    private String verifyCode;
}
