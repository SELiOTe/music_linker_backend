package com.seliote.mlb.biz.domain.si.user;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

/**
 * 查找用户信息 SI
 *
 * @author seliote
 * @version 2021-08-22
 */
@Data
public class FindUserSi {

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @TelNo
    private String telNo;
}
