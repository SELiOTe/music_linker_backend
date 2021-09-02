package com.seliote.mlb.biz.domain.si.user;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

/**
 * 用户是否注册 SI
 *
 * @author seliote
 * @version 2021-08-22
 */
@Data
public class IsSignedUpSi {

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @TelNo
    private String telNo;
}
