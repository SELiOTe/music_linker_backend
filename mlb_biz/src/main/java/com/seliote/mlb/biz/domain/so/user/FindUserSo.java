package com.seliote.mlb.biz.domain.so.user;

import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查找用户信息 SO
 *
 * @author seliote
 * @version 2021-09-04
 */
@Data
public class FindUserSo {

    // 用户 ID
    @NotNull
    private Long userId;

    // 国家码
    @CountryCode
    private String countryCode;

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码
    @TelNo
    private String telNo;
}
