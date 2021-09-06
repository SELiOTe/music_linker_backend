package com.seliote.mlb.biz.domain.si.user;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.PlaintextPassword;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;
import lombok.ToString;

/**
 * 校验用户密码 SI
 *
 * @author seliote
 * @version 2021-09-06
 */
@Data
public class CheckUserPasswordSi {

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @TelNo
    private String telNo;

    // 明文密码
    @ToString.Exclude
    @PlaintextPassword
    private String password;
}
