package com.seliote.mlb.biz.domain.si.common;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

/**
 * 移除注册短信验证码 SI
 *
 * @author seliote
 * @version 2021-09-02
 */
@Data
public class RemoveSignUpSmsSi {

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @TelNo
    private String telNo;
}
