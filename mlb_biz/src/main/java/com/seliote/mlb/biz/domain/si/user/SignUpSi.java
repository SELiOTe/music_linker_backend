package com.seliote.mlb.biz.domain.si.user;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.Nickname;
import com.seliote.mlb.common.jsr303.userinfo.PlaintextPassword;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;
import lombok.ToString;

/**
 * 普通用户注册 SI
 *
 * @author seliote
 * @version 2021-09-03
 */
@Data
public class SignUpSi {

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

    // 用户昵称
    @Nickname
    private String nickname;
}
